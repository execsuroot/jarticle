package tech.execsuroot.template.config;

import de.exlll.configlib.Configuration;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents the main configuration file.
 * It is used to store general configuration options.
 */
@Getter
@Setter
@Configuration
public class MainConfig {

    /**
     * The instance of the main configuration file.
     * Use to access the configuration.
     */
    @Getter
    @Setter
    private static MainConfig instance;

    // ToDo: Include your general config options here
    private String example;
}
