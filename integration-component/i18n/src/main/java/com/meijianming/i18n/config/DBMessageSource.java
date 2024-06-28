package com.meijianming.i18n.config;

import com.meijianming.i18n.entity.LanguageEntity;
import com.meijianming.i18n.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Locale;

@Component("messageSource")
public class DBMessageSource extends AbstractMessageSource {
	@Autowired
	private LanguageRepository languageRepository;

	@Override
	protected MessageFormat resolveCode(String key, Locale locale) {
		LanguageEntity message = languageRepository.findByKeyAndLocale(key,locale.getLanguage());
		if (message == null) {
			message = languageRepository.findByKeyAndLocale(key,Locale.getDefault().getLanguage());
		}
		return new MessageFormat(message.getContent(), locale);
	}

	//新增方法，用于后端传参国际化
	public final String getMessage(String code, @Nullable Object[] args) throws NoSuchMessageException {
		Locale locale = LocaleContextHolder.getLocale();
		String msg = getMessageInternal(code, args, locale);
		if (msg != null) {
			return msg;
		}
		String fallback = getDefaultMessage(code);
		if (fallback != null) {
			return fallback;
		}
		throw new NoSuchMessageException(code, locale);
	}
}
