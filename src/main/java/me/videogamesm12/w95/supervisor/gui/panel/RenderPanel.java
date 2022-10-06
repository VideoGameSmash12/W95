package me.videogamesm12.w95.supervisor.gui.panel;

import me.videogamesm12.w95.entrypoints.W95;
import me.videogamesm12.w95.templates.WModule;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RenderPanel extends JPanel
{
    public JLabel description = new JLabel();
    //
    public JCheckBox disableWorldRendering = new JCheckBox();
    public JCheckBox disableEntityRendering = new JCheckBox();
    public JCheckBox disableBlockEntityRendering = new javax.swing.JCheckBox();
    public JCheckBox disableRenderingCompletely = new JCheckBox();

    public RenderPanel()
    {
        description.setText("Need to stop something from being rendered to fix a lag issue? If so, then this is the "
                + "page to do it.");
        //
        disableWorldRendering.setText("Disable World Rendering");
        disableEntityRendering.setText("Disable Entity Rendering");
        disableBlockEntityRendering.setText("Disable Block Entity Rendering");
        disableRenderingCompletely.setText("Disable Rendering Completely");
        //
        disableEntityRendering.setToolTipText("Prevents the client from rendering entities, including mobs, players, "
                + "minecarts, and item frames.");
        disableBlockEntityRendering.setToolTipText("Prevents the client from rendering block entities, such as chests, "
                + "player heads, and shulker boxes.");
        disableRenderingCompletely.setToolTipText("Prevents the client from rendering anything period.");
        //
        disableWorldRendering.addActionListener(e -> update());
        disableEntityRendering.addActionListener(e -> update());
        disableBlockEntityRendering.addActionListener(e -> update());
        disableRenderingCompletely.addActionListener(e -> update());
        //
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        //
        GroupLayout.ParallelGroup xGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        GroupLayout.SequentialGroup yGroup = layout.createSequentialGroup();
        //
        xGroup.addComponent(description);
        xGroup.addComponent(disableWorldRendering);
        xGroup.addComponent(disableEntityRendering);
        xGroup.addComponent(disableBlockEntityRendering);
        xGroup.addComponent(disableRenderingCompletely);
        //
        yGroup.addContainerGap().addComponent(description);
        yGroup.addContainerGap().addComponent(disableWorldRendering);
        yGroup.addContainerGap().addComponent(disableEntityRendering);
        yGroup.addContainerGap().addComponent(disableBlockEntityRendering);
        yGroup.addContainerGap().addComponent(disableRenderingCompletely);
        //
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(xGroup)));
        //
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(yGroup)
        );
    }

    public void update()
    {
        W95.instance.getConfig().supervisor.disableWorldRendering = disableWorldRendering.isSelected();
        W95.instance.getConfig().supervisor.disableEntityRendering = disableEntityRendering.isSelected();
        W95.instance.getConfig().supervisor.disableBlockEntities = disableBlockEntityRendering.isSelected();
        W95.instance.getConfig().supervisor.disableRendering = disableRenderingCompletely.isSelected();
    }
}
