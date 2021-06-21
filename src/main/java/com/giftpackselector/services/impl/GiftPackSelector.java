/**
 * 
 */
package com.giftpackselector.services.impl;

import static java.lang.Thread.currentThread;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.giftpackselector.constants.GiftPackSelectorConstants;
import com.giftpackselector.constants.GiftPackSelectorErrorConstants;
import com.giftpackselector.exceptions.GiftPackSelectorExceptions;
import com.giftpackselector.models.GiftItem;
import com.giftpackselector.services.IGiftPackSelector;

/**
 * 
 * This service class handles all processing related to gift item packaging
 * 
 * @author Narendra.Kumar
 *
 */
public class GiftPackSelector implements IGiftPackSelector {

	/**
	 * Logger
	 */
	private static final Logger LOGGER = LogManager.getLogger(GiftPackSelector.class);

	/**
	 * This method is used to return the selected items which are are ready to send.
	 * 
	 * @param fileName
	 * @return
	 */
	public String packetsReadyToSend(String fileName)  {
		StringBuilder packetReadyToSend = new StringBuilder();
		File file = getFile(fileName);
		if (null != file) {
			Map<String, String> giftItemMap = processFile(file);
			giftItemMap.forEach((weight, itemSn) -> packetReadyToSend.append("\n" + itemSn + "\n"));
		}
		return packetReadyToSend.toString();
	}

	/**
	 * This method is used to process the file and fetching the required result.
	 * fetch only given max count record e.g. 15 totalWeight is less than given max
	 * weight e.g. 100
	 */
	private Map<String, String> processFile(File file) throws GiftPackSelectorExceptions {
		Map<String, String> giftItemMap = new LinkedHashMap<>();
		try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
			;
			bufferedReader.lines().forEach(line -> {
				if (null != line && !line.isBlank()) {
					String[] weightAndRawItems = line.split(GiftPackSelectorConstants.COLON);
					String totalWeightStr = weightAndRawItems[0].trim();
					double totalWeight = Double.parseDouble(totalWeightStr);
					if (totalWeight > GiftPackSelectorConstants.MAX_PACKAGE_WEIGHT) {
						LOGGER.error("processFile()  Total Package weight limit exceeded.");
						throw new GiftPackSelectorExceptions(GiftPackSelectorErrorConstants.MAX_WEIGHT_ERROR_CODE,
								GiftPackSelectorErrorConstants.MAX_WEIGHT_ERROR_MESSAGE);
					}
					String itemsStr = weightAndRawItems[1];
					Matcher countMatcher = GiftPackSelectorConstants.pattern.matcher(itemsStr);

					long count = countMatcher.results().count();
					if (GiftPackSelectorConstants.MAX_ITEMS_COUNT < count) {
						LOGGER.error("processFile()  Total package count limit exceeded.");
						throw new GiftPackSelectorExceptions(
								GiftPackSelectorErrorConstants.MAX_PACKAGE_COUNT_ERROR_CODE,
								GiftPackSelectorErrorConstants.MAX_PACKAGE_COUNT_ERROR_MESSAGE);
					}
					countMatcher.reset();
					List<GiftItem> giftItems = prepareGiftItemList(itemsStr, totalWeight);
					String selectedItems = fetchItems(giftItems, totalWeight);
					giftItemMap.put(totalWeightStr, selectedItems);

				}
			});
		} catch (NumberFormatException nfe) {
			LOGGER.error("processFile() Exception occured while parsing the file-" + file.getName());
			throw new GiftPackSelectorExceptions(GiftPackSelectorErrorConstants.INVALID_INPUT_FORMAT_ERROR_CODE,
					GiftPackSelectorErrorConstants.INVALID_INPUT_FORMAT_ERROR_MESSAGE);
		} catch (IOException ex) {
			LOGGER.error("processFile()  Exception occured while processing the file.");
			throw new GiftPackSelectorExceptions(GiftPackSelectorErrorConstants.FILE_NAME_LOCATION_ERROR_CODE,
					GiftPackSelectorErrorConstants.FILE_NAME_LOCATION_ERROR_MESSAGE);
		}
		return giftItemMap;

	}

	/**
	 * select gift items those cost is less than given max cost and item weight is
	 * less than given max weight
	 * 
	 * @param itemsStr
	 * @param totalWeight
	 * @return
	 */
	private List<GiftItem> prepareGiftItemList(String itemsStr, double totalWeight) throws NumberFormatException {
		// try {
		Matcher resultMatcher = GiftPackSelectorConstants.pattern.matcher(itemsStr);
		List<GiftItem> giftItems = resultMatcher.results().map(matcherResult -> {
			GiftItem giftItem = null;
			String itemStr = matcherResult.group(1);
			if (null != itemStr && !itemStr.isBlank()) {
				String[] itemStrArr = itemStr.trim().split(",");
				int itemSn = Integer.parseInt(itemStrArr[0]);
				Double itemWeight = Double.parseDouble(itemStrArr[1]);
				String itemCostStr = itemStrArr[2].substring(1).trim();
				Integer itemCost = Integer.valueOf(itemCostStr);
				if (itemCost > GiftPackSelectorConstants.MAX_ITEM_COST
						|| itemWeight > GiftPackSelectorConstants.MAX_ITEM_WEIGHT) {
					LOGGER.error("prepareGiftItemList() "
							+ GiftPackSelectorErrorConstants.GIFT_ITEM_WEIGHT_OR_AND_COST_LIMIT_EXCEEDED_ERROR_MESSAGE);

					throw new GiftPackSelectorExceptions(
							GiftPackSelectorErrorConstants.GIFT_ITEM_WEIGHT_OR_AND_COST_LIMIT_EXCEEDED_ERROR_CODE,
							GiftPackSelectorErrorConstants.GIFT_ITEM_WEIGHT_OR_AND_COST_LIMIT_EXCEEDED_ERROR_MESSAGE);
				}
				giftItem = new GiftItem(itemSn, itemWeight, itemCost);

			}
			return giftItem;

		}).collect(toList());
		resultMatcher.reset();
		return giftItems;

	}

	/**
	 * This method is used fetch highest cost gift item index those weight sum is
	 * less than equal to total item weight(81) e.g. 81:(....)
	 * 
	 * @param giftItemList
	 * @param totalWeight
	 * @return
	 */
	private String fetchItems(List<GiftItem> giftItemList, double totalWeight) {
		double packetWeight = 0d;
		StringJoiner packageList = new StringJoiner(",");
		List<GiftItem> sortedByCostDescList = giftItemList.stream()
				.filter(giftItem -> giftItem.getWeight() < totalWeight)
				.sorted(Comparator.comparing(GiftItem::getCost).reversed()).collect(Collectors.toList());
		for (GiftItem giftItem : sortedByCostDescList) {
			packetWeight += giftItem.getWeight();
			if (packetWeight <= totalWeight) {
				packageList.add(giftItem.getSn().toString());
			} else {
				packetWeight -= giftItem.getWeight();
			}

		}
		if (packageList.length() < 1)
			packageList.add("-");
		return packageList.toString();
	}

	/**
	 * This method is used to read the file from resources folder using fileName.
	 * 
	 * @param fileName
	 * @return
	 */
	private File getFile(String fileName) throws GiftPackSelectorExceptions {
		try {
			ClassLoader classloader = currentThread().getContextClassLoader();
			return new File(requireNonNull(classloader.getResource(fileName)).getFile());
		} catch (Exception ex) {
			LOGGER.error("getFile()  Exception occured while processing the file.");
			throw new GiftPackSelectorExceptions(GiftPackSelectorErrorConstants.FILE_NAME_LOCATION_ERROR_CODE,
					GiftPackSelectorErrorConstants.FILE_NAME_LOCATION_ERROR_MESSAGE);
		}

	}

}
