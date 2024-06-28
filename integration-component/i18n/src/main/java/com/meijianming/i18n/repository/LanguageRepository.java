package com.meijianming.i18n.repository;

import com.meijianming.i18n.entity.LanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<LanguageEntity, Integer> {

    LanguageEntity findByKeyAndLocale(String key, String locale);

}
