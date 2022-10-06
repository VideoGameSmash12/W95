package me.videogamesm12.w95.supervisor.gui.panel;

import me.videogamesm12.w95.events.SupervisorEvents;
import me.videogamesm12.w95.supervisor.gui.SupervisorFrame;
import me.videogamesm12.w95.utilities.Formatter;
import net.minecraft.entity.Entity;
import net.minecraft.util.ActionResult;

import javax.swing.*;
import java.util.List;

public class EntitiesPanel extends JPanel implements SupervisorEvents.UpdateEntities
{
    public JList<String> entities = new JList<>();
    public JScrollPane pane = new JScrollPane();

    public EntitiesPanel()
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

        SupervisorEvents.UPDATE_ENTITIES.register(this);
    }

    @Override
    public ActionResult onUpdateEntities(List<Entity> entities)
    {
        if (!isVisible())
        {
            return ActionResult.PASS;
        }

        this.entities.setListData(Formatter.entitiesListToStringArray(entities));
        /*DefaultListModel<String> model = (DefaultListModel<String>) this.entities.getModel();
        model.removeAllElements();

        int i = 0;

        for (Entity entity : entities)
        {
            model.add(i, Formatter.entityToString(entity));
            i++;
        }*/

        return ActionResult.PASS;
    }
}
