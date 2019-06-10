package org.dzendzula.dbviewer.repository;

import org.dzendzula.dbviewer.domain.DbConnectionSettingsBo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DbConnectionSettingsRepository extends JpaRepository<DbConnectionSettingsBo, Long> {
}
