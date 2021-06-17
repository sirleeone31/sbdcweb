package com.sbdc.sbdcweb.mail.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

/**
 * TB_QNA_EMAIL Domain
 *
 * @author 	: 이승희
 * @version : 1.0
 * @since 	: 1.0
 * @date    : 2019-10-22
 */
@DynamicUpdate
@Data
@Entity
@Table(name = "TB_QNA_EMAIL_NEW")
public class QnaEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMAIL_KEY")
    private Long emailKey;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "NAME")
    private String name;

    public QnaEmail() {
        this.email = "";
        this.name = "";
    }

    public QnaEmail(Long emailKey, String email, String name) {
        this.emailKey = emailKey;
        this.email = email;
        this.name = name;
    }

    @Override
	public String toString() {
		return String.valueOf(emailKey);
	}

}