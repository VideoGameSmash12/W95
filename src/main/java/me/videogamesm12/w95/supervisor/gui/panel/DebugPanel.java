package me.videogamesm12.w95.supervisor.gui.panel;

import me.videogamesm12.w95.events.SupervisorEvents;
import net.minecraft.util.ActionResult;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DebugPanel extends JPanel implements SupervisorEvents.UpdateDebugInfo
{
    public JTextArea label = new JTextArea();
    public JScrollPane pane = new JScrollPane();

    public DebugPanel()
    {
        label.setEditable(false);
        label.setFont(new Font("Courier New", Font.PLAIN, 12));

        pane.setViewportView(label);
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

        SupervisorEvents.UPDATE_DEBUG_INFO.register(this);
    }

    @Override
    public ActionResult onUpdateDebugInfo(List<String> left)
    {
        if (!isVisible())
        {
            return ActionResult.PASS;
        }

        StringBuilder builder = new StringBuilder();
        for (String line : left)
        {
            builder.append(line).append("\n");
        }
        label.setText(builder.toString());

        return ActionResult.PASS;
    }
}
