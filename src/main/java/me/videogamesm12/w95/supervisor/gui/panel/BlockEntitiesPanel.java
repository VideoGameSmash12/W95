package me.videogamesm12.w95.supervisor.gui.panel;

import me.videogamesm12.w95.events.SupervisorEvents;
import me.videogamesm12.w95.utilities.Formatter;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.ActionResult;

import javax.swing.*;
import java.util.List;

public class BlockEntitiesPanel extends JPanel implements SupervisorEvents.UpdateBlockEntities
{
    public JList<String> entities = new JList<>();
    public JScrollPane pane = new JScrollPane();

    public BlockEntitiesPanel()
    {
        //pane.setEnabled(true);
        //pane.add(entities);
        pane.setViewportView(entities);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addComponent(pane, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 762, Short.MAX_VALUE))
                                .addContainerGap()))));

        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addComponent(pane, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE))
                                .addContainerGap()))));

        SupervisorEvents.UPDATE_BLOCK_ENTITIES.register(this);
    }

    @Override
    public ActionResult onUpdateBlockEntities(List<BlockEntity> entities)
    {
        if (!isVisible())
        {
            return ActionResult.PASS;
        }

        this.entities.setListData(Formatter.blockEntitiesListToStringArray(entities));
        return ActionResult.PASS;
    }
}
