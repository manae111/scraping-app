package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.example.demo.controller.ScrapingController;
import com.example.demo.domain.Item;
import com.example.demo.repository.ScrapingRepository;

@Service
public class ScrapingService {

	private static final Logger logger = LoggerFactory.getLogger(ScrapingController.class);

	@Autowired
	private MailSender sender;

	@Autowired
	private ScrapingRepository scrapingRepository;

	/**
	 * 登録時priceよりもバッチ処理後priceが安い商品一覧のメールを送信するメソッド
	 * 
	 * @param email
	 */
	public void sendMail() {
		List<Item> itemList = scrapingRepository.findAll();
		List<Item> updateItemList = new ArrayList<>();
		for (Item item : itemList) {
			if (item.getPriceOriginal() > item.getPriceLatest()) {
				updateItemList.add(item);
			}
		}

		if (updateItemList.size() != 0) {

			try {
				SimpleMailMessage msg = new SimpleMailMessage();
				msg.setFrom("on99.matsunaga.dai@gmail.com");
				msg.setTo("testtestsc@gmail.com");
				msg.setSubject("価格変更がある商品のご案内");// タイトルの設定
				String template = "=======================================\n" +
                  "商品名:\n" +
                  "{itemName}\n\n" +
                  "URL:\n" +
                  "{url}\n\n" +
                  "登録時の価格:\n" +
                  "{priceOriginal}円\n\n" +
                  "現在の価格:\n" +
                  "{priceLatest}円\n\n" +
                  "=======================================";

				String message = updateItemList.stream()
				  .map(updateItem -> template.replace("{itemName}", updateItem.getItemName())
											 .replace("{url}", updateItem.getUrl())
											 .replace("{priceOriginal}", String.valueOf(updateItem.getPriceOriginal()))
											 .replace("{priceLatest}", String.valueOf(updateItem.getPriceLatest())))
				  .collect(Collectors.joining("\n"));
			  	msg.setText(message);
				sender.send(msg);
				logger.info(updateItemList.size()+ "件の価格変更がありました");


			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			System.out.println("更新された商品はありません");

		}

	}

	public void insert(Item item) {
		scrapingRepository.insert(item);
	}

	public void update(String url, Integer price) {
		scrapingRepository.update(url, price);
	}

	public List<String> findAllUrl() {
		List<String> urlList = scrapingRepository.findAllUrl();
		return urlList;
	}

	public Integer findPriceOriginal(String url) {
		Integer priceOriginal = scrapingRepository.findPriceOriginal(url);
		return priceOriginal;
	}

	public List<Item> findAll() {
		List<Item> itemList = scrapingRepository.findAll();
		return itemList;
	}
}
