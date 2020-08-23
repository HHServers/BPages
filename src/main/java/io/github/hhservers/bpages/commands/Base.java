package io.github.hhservers.bpages.commands;

import io.github.hhservers.bpages.BPages;
import io.github.hhservers.bpages.config.MainPluginConfig;
import io.github.hhservers.bpages.util.PageObject;
import org.apache.commons.lang3.text.WordUtils;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;

import javax.swing.text.TextAction;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Base implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        MainPluginConfig conf = BPages.getMainPluginConfig();
        if(args.hasAny(Text.of("pageID"))){
            String pageID = args.<String>getOne(Text.of("pageID")).get().toLowerCase();
            if(src.hasPermission("bpages.page."+pageID)) {
                BPages.pageMap.get(pageID).sendTo(src);
            } else {src.sendMessage(Text.of(TextColors.RED, "You do not have permission to view this Page!"));}
        } else {
            List<Text> textList = new ArrayList<>();
            for(PageObject obj : conf.getPageListNode().getPageList()){
                String s = WordUtils.capitalize(obj.getCommandAlias());
                textList.add(TextSerializers.FORMATTING_CODE.deserialize(conf.getMainPagePrefix()+s).toBuilder()
                .onHover(TextActions.showText(Text.of("Click me to open this page")))
                .onClick(TextActions.runCommand("/"+conf.getCommandAlias()+" "+obj.getCommandAlias()))
                .build());
            }
            PaginationList.builder()
                    .title(TextSerializers.FORMATTING_CODE.deserialize(conf.getMainPageTitle()))
                    .contents(textList)
                    .padding(TextSerializers.FORMATTING_CODE.deserialize(conf.getMainPagePadding()))
                    .linesPerPage(conf.getMainLinesPerPage())
                    .sendTo(src);
        }
        return CommandResult.success();
    }

    public static CommandSpec build(){
       return CommandSpec.builder()
                .arguments(GenericArguments.optional(GenericArguments.string(Text.of("pageID"))))
                .permission("bpages.user.base")
                .child(Reload.build(), "reload")
                .description(Text.of("Base command"))
                .executor(new Base())
                .build();
    }
}
