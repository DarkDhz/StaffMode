package dev.darkhorizon.es.sm.config;

import dev.darkhorizon.es.sm.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class FileManger {

    private static final Main plugin = Main.getPlugin(Main.class);
    private static FileManger INSTANCE = null;

    private FileManger() {
        //TODO Singleton for only 1 object instance
        //this.initFiles();
    }

    public static FileManger getInstance() {
        if (INSTANCE == null) createInstance();
        return INSTANCE;
    }

    private static void createInstance() {
        if (INSTANCE == null) {
            synchronized(FileManger.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FileManger();
                }
            }
        }
    }

    public static void initFiles() {
        initLangConfig();
        initConfigConfig();
        initHotbarConfig();
        initGUIConfig();
    }


    private static File lang;
    private static FileConfiguration langConfig;

    private static void initLangConfig() {
        lang = new File(plugin.getDataFolder(), "lang.yml");
        if (!lang.exists()) {
            lang.getParentFile().mkdirs();
            plugin.saveResource("lang.yml", false);
        }

        langConfig = new YamlConfiguration();
        try {
            FileInputStream fileinputstream = new FileInputStream(lang);
            langConfig.load(new InputStreamReader(fileinputstream, StandardCharsets.UTF_8));
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getLang() {
        return langConfig;
    }

    private static File config;
    private static FileConfiguration configConfig;

    private static void initConfigConfig() {
        config = new File(plugin.getDataFolder(), "config.yml");
        if (!config.exists()) {
            config.getParentFile().mkdirs();
            plugin.saveResource("config.yml", false);
        }

        configConfig = new YamlConfiguration();
        try {
            FileInputStream fileinputstream = new FileInputStream(config);
            configConfig.load(new InputStreamReader(fileinputstream, StandardCharsets.UTF_8));
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfig() {
        return configConfig;
    }

    private static File hotbar;
    private static FileConfiguration hotbarConfig;


    private static void initHotbarConfig() {
        hotbar = new File(plugin.getDataFolder(), "hotbar.yml");
        if (!hotbar.exists()) {
            hotbar.getParentFile().mkdirs();
            plugin.saveResource("hotbar.yml", false);
        }

        hotbarConfig = new YamlConfiguration();
        try {
            FileInputStream fileinputstream = new FileInputStream(hotbar);
            hotbarConfig.load(new InputStreamReader(fileinputstream, StandardCharsets.UTF_8));
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getHotbar() {
        return hotbarConfig;
    }

    private static File gui;
    private static FileConfiguration guiConfig;


    private static void initGUIConfig() {
        gui = new File(plugin.getDataFolder(), "gui.yml");
        if (!gui.exists()) {
            gui.getParentFile().mkdirs();
            plugin.saveResource("gui.yml", false);
        }

        guiConfig = new YamlConfiguration();
        try {
            FileInputStream fileinputstream = new FileInputStream(gui);
            guiConfig.load(new InputStreamReader(fileinputstream, StandardCharsets.UTF_8));
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getGUI() {
        return guiConfig;
    }


}
