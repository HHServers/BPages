package io.github.hhservers.bpages;

import com.google.inject.Inject;
import io.github.hhservers.bpages.commands.Base;
import io.github.hhservers.bpages.config.ConfigHandler;
import io.github.hhservers.bpages.config.MainPluginConfig;
import io.github.hhservers.bpages.util.PageBuilder;
import lombok.Getter;
import ninja.leaping.configurate.objectmapping.GuiceObjectMapperFactory;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.pagination.PaginationList;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Plugin(
        id = "bpages",
        name = "BPages",
        description = "Pages plugin",
        version = "1.2",
        authors = {
                "blvxr"
        }
)
public class BPages {

    @Getter
    private static BPages instance;
    @Getter
    @Inject
    private Logger logger;
    @Getter
    private static MainPluginConfig mainPluginConfig;
    private final GuiceObjectMapperFactory factory;
    private final File configDir;
    private static ConfigHandler configHandler;
    public static Map<String, PaginationList> pageMap = new HashMap<>();


    @Inject
    public BPages(GuiceObjectMapperFactory factory, @ConfigDir(sharedRoot = false) File configDir) {
        this.factory=factory;
        this.configDir=configDir;
        instance=this;
    }

    @Listener
    public void onGamePreInit(GamePreInitializationEvent e) throws IOException, ObjectMappingException {
        reloadConfig();
    }

    @Listener
    public void onGameInit(GameInitializationEvent e){
        instance = this;
        Sponge.getCommandManager().register(instance, Base.build(), "page");
    }

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        new PageBuilder().buildPage();
    }

    @Listener
    public void onGameReload(GameReloadEvent e) throws IOException, ObjectMappingException {
        reloadConfig();
        pageMap.clear();
        new PageBuilder().buildPage();
    }

    public void reloadConfig() throws IOException, ObjectMappingException {
        configHandler=new ConfigHandler(this);
        if (configHandler.loadConfig()) {mainPluginConfig = configHandler.getPluginConf();}
    }

    public GuiceObjectMapperFactory getFactory() {
        return factory;
    }

    public File getConfigDir() {
        return configDir;
    }
}
