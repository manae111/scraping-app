package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Item;
import com.example.demo.repository.ScrapingRepository;

@Service
public class ScrapingService {

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
			SimpleMailMessage msg = new SimpleMailMessage();

			msg.setFrom("on99.matsunaga.dai@gmail.com");
			msg.setTo("testtestsc@gmail.com");
			msg.setSubject("テスト！！！");// タイトルの設定
			String updateItemListText = updateItemList.stream()
					.map(item -> item.getUrl() + " : " + item.getItemName() + " : " + item.getPriceOriginal() + " : " + item.getPriceLatest())
					.collect(Collectors.joining(", "));
			msg.setText(updateItemListText);

			this.sender.send(msg);
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
