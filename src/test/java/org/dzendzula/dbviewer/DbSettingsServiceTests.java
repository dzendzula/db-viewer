package org.dzendzula.dbviewer;

import org.dzendzula.dbviewer.controller.dto.DbConnectionSettingsDto;
import org.dzendzula.dbviewer.controller.exceptions.NotFoundValidationException;
import org.dzendzula.dbviewer.service.DbConnectionSettingsService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class DbSettingsServiceTests {

    @Autowired
    private DbConnectionSettingsService settingsService;


    @Test
    public void testCreateSettings() {
        DbConnectionSettingsDto dto = createFooSettings();
        DbConnectionSettingsDto created = settingsService.createDbConnectionSettings(dto);
        Assert.assertNotNull(created);
        Assert.assertNotNull(created.getId());
        dto.setId(created.getId());
        Assert.assertEquals(dto, created);
    }

    @Test
    public void testUpdateSettings() {
        DbConnectionSettingsDto dto = createFooSettings();
        DbConnectionSettingsDto created = settingsService.createDbConnectionSettings(dto);
        Assert.assertNotNull(created);
        Assert.assertNotNull(created.getId());
        dto.setId(created.getId());
        dto.setDbName("updated_name");
        dto.setHost("updated_host");
        dto.setUsername("updated_user");
        dto.setPassword("updated_password");
        dto.setPort(4321);
        DbConnectionSettingsDto updated = settingsService.updateDbConnectionSettings(dto.getId(), dto);
        Assert.assertEquals(dto, updated);
    }

    @Test(expected = NotFoundValidationException.class)
    public void testDeleteSettings() {
        DbConnectionSettingsDto dto = createFooSettings();
        DbConnectionSettingsDto created = settingsService.createDbConnectionSettings(dto);
        Assert.assertNotNull(created);
        Assert.assertNotNull(created.getId());
        settingsService.deleteDbConnectionSettings(created.getId());
        settingsService.findDbConnectionSettings(created.getId());
    }


    private DbConnectionSettingsDto createFooSettings() {
        DbConnectionSettingsDto result = new DbConnectionSettingsDto();
        result.setName("test_name");
        result.setHost("test_host_name");
        result.setPort(1234);
        result.setDbName("test_db_name");
        result.setUsername("test_username");
        result.setPassword("test_password");
        return result;
    }

}
