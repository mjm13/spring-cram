package com.meijianming.i18n.entity;

import javax.persistence.*;

@Entity
@Table(name = "languages")
public class LanguageEntity {

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@Column
	private Integer id;

	@Column
	private String locale;

	@Column(name = "messagekey")
	private String key;

	@Column(name = "messagecontent")
	private String content;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
