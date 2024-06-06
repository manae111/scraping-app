package com.example.demo.service;

import org.checkerframework.checker.units.qual.s;
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
	 * メールピットに定時メールを送付
	 * 
	 * @param email
	 */
	public void sendMail() {
		SimpleMailMessage msg = new SimpleMailMessage();

		msg.setFrom("on99.matsunaga.dai@gmail.com");
		msg.setTo("testtestsc@gmail.com");
		msg.setSubject("テスト！！！");// タイトルの設定
		msg.setText("これは本文です"); // 本文の設定

		this.sender.send(msg);
	}

    public void insert(Item item) {
        scrapingRepository.insert(item);
    }

    public Item update(Item item) {
        return scrapingRepository.update(item);
    }

}
