package org.dzendzula.restfilterindex.repository;

import org.dzendzula.restfilterindex.domain.DbConnectionSettingsBo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DbConnectionSettingsRepository extends JpaRepository<DbConnectionSettingsBo, Long> {
}
