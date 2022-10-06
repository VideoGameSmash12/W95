package me.videogamesm12.w95.supervisor.gui;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialDarkerIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialLighterIJTheme;
import me.videogamesm12.w95.entrypoints.W95;
import me.videogamesm12.w95.events.W95Events;
import me.videogamesm12.w95.supervisor.Supervisor;
import me.videogamesm12.w95.supervisor.gui.menu.DumpMenu;
import me.videogamesm12.w95.supervisor.gui.menu.ModulesMenu;
import me.videogamesm12.w95.supervisor.gui.panel.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class SupervisorFrame extends JFrame implements W95Events.DumpEntitiesSuccessful, W95Events.DumpEntitiesFailed
{
    public static SupervisorFrame instance;
    //--
    public static boolean disableDarkModes = false;

    public SupervisorFrame()
    {
        initComponents();

        //SupervisorEvents.SETTING_CHANGED.register(this);
        W95Events.DUMP_ENTITIES_SUCCESS.register(this);
        W95Events.DUMP_ENTITIES_FAILED.register(this);

        webPage.setEditable(false);
        try
        {
            webPage.setPage("https://videogamesm12.me/w95/recovery.html");
        }
        catch (IOException ex)
        {
            Supervisor.logger.error(ex);
        }
        instance = this;
    }

    private void initComponents()
    {
        tabbedPane = new javax.swing.JTabbedPane();
        homePane = new javax.swing.JScrollPane();
        webPage = new javax.swing.JEditorPane();
        mitigationsPanel = new javax.swing.JPanel();
        renderPanel = new javax.swing.JPanel();
        disableLighting = new javax.swing.JCheckBox();
        disableEntityNames = new javax.swing.JCheckBox();
        descriptionLabel = new javax.swing.JLabel();
        networkPanel = new javax.swing.JPanel();
        packetBlacklist = new javax.swing.JCheckBox();
        editBlacklistButton = new javax.swing.JButton();
        reloadPanel = new javax.swing.JPanel();
        drasticMeasuresPanel = new javax.swing.JPanel();
        disconnectRadio = new javax.swing.JRadioButton();
        exitRadio = new javax.swing.JRadioButton();
        forcefullyExitRadio = new javax.swing.JRadioButton();
        goButton = new javax.swing.JButton();
        descriptionTwo = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        modulesMenu = new ModulesMenu();
        dumpMenu = new DumpMenu();
        buttonGroup = new ButtonGroup();
        entitiesPanel = new EntitiesPanel();
        blockEntitiesPanel = new BlockEntitiesPanel();
        debugPanel = new DebugPanel();
        chatPanel = new ChatPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Supervisor");
        setMinimumSize(new java.awt.Dimension(854, 480));
        setType(java.awt.Window.Type.POPUP);

        tabbedPane.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        homePane.setBorder(null);

        webPage.setBorder(null);
        webPage.setContentType("text/html"); // NOI18N
        try
        {
            webPage.setPage(new java.net.URL("https://videogamesm12.me/w95/recovery.html"));
        }
        catch (java.io.IOException e1)
        {
            e1.printStackTrace();
        }
        homePane.setViewportView(webPage);

        tabbedPane.addTab("Home", homePane);
        tabbedPane.addTab("Chat", chatPanel);
        tabbedPane.addTab("Rendering", new RenderPanel());

        renderPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Rendering"));

        /*disable3DRendering.setText("Disable World Rendering");
        disable3DRendering.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                W95.instance.getConfig().supervisor.disableWorldRendering = disable3DRendering.isSelected();
            }
        });
        disable3DRendering.setSelected(W95.instance.getConfig().supervisor.disableWorldRendering);*/

        /*disableEntityRendering.setText("Disable Entity Rendering");
        disableEntityRendering.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                W95.instance.getConfig().supervisor.disableEntityRendering = disableEntityRendering.isSelected();
            }
        });
        disableEntityRendering.setSelected(W95.instance.getConfig().supervisor.disableEntityRendering);*/

        disableLighting.setText("Disable Light Updates");
        disableLighting.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                W95.instance.getConfig().supervisor.disableLighting = disableLighting.isSelected();
            }
        });
        disableLighting.setSelected(W95.instance.getConfig().supervisor.disableLighting);

        disableEntityNames.setText("Disable Custom Names");
        disableEntityNames.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                W95.instance.getConfig().supervisor.disableCustomNames = disableEntityNames.isSelected();
            }
        });
        disableEntityNames.setSelected(W95.instance.getConfig().supervisor.disableCustomNames);

        /*disableGameRendering.setText("Disable Rendering Completely");
        disableGameRendering.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                W95.instance.getConfig().supervisor.disableRendering = disableGameRendering.isSelected();
            }
        });
        disableGameRendering.setSelected(W95.instance.getConfig().supervisor.disableRendering);*/

        javax.swing.GroupLayout renderPanelLayout = new javax.swing.GroupLayout(renderPanel);
        renderPanel.setLayout(renderPanelLayout);
        renderPanelLayout.setHorizontalGroup(
                renderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(renderPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(renderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        /*.addComponent(disable3DRendering)*/
                                        /*.addComponent(disableEntityRendering)*/
                                        .addComponent(disableLighting)
                                        .addComponent(disableEntityNames)
                                        /*.addComponent(disableGameRendering)*/)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        renderPanelLayout.setVerticalGroup(
                renderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(renderPanelLayout.createSequentialGroup()
                                /*.addComponent(disable3DRendering)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                /*.addComponent(disableEntityRendering)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)*/
                                .addComponent(disableLighting)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(disableEntityNames)
                                /*.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(disableGameRendering)*/
                                .addContainerGap(86, Short.MAX_VALUE))
        );

        descriptionLabel.setText("These options allow you to disable potentially performance-heavy portions of the game to help it catch up in the event of a freeze.");

        networkPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Network"));
        networkPanel.setPreferredSize(new java.awt.Dimension(318, 160));

        packetBlacklist.setText("Packet Blacklist (not implemented)");
        packetBlacklist.setEnabled(false);

        editBlacklistButton.setText("Edit Blacklist");
        editBlacklistButton.setEnabled(false);

        javax.swing.GroupLayout networkPanelLayout = new javax.swing.GroupLayout(networkPanel);
        networkPanel.setLayout(networkPanelLayout);
        networkPanelLayout.setHorizontalGroup(
                networkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(networkPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(packetBlacklist)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(editBlacklistButton)
                                .addContainerGap())
        );
        networkPanelLayout.setVerticalGroup(
                networkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(networkPanelLayout.createSequentialGroup()
                                .addGroup(networkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(packetBlacklist)
                                        .addComponent(editBlacklistButton))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        reloadPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Reloading"));
        reloadPanel.setPreferredSize(new java.awt.Dimension(318, 160));

        javax.swing.GroupLayout reloadPanelLayout = new javax.swing.GroupLayout(reloadPanel);
        reloadPanel.setLayout(reloadPanelLayout);
        reloadPanelLayout.setHorizontalGroup(
                reloadPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 352, Short.MAX_VALUE)
        );
        reloadPanelLayout.setVerticalGroup(
                reloadPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 183, Short.MAX_VALUE)
        );

        drasticMeasuresPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Drastic Measures"));
        drasticMeasuresPanel.setPreferredSize(new java.awt.Dimension(318, 160));

        disconnectRadio.setText("Disconnect");

        exitRadio.setText("Exit");

        forcefullyExitRadio.setText("Forcefully Exit");

        goButton.setText("Go");
        goButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (buttonGroup.isSelected(disconnectRadio.getModel()))
                {
                    Supervisor.logger.info("Disconnecting...");
                    if (MinecraftClient.getInstance().getNetworkHandler() != null
                            && MinecraftClient.getInstance().getNetworkHandler().getConnection() != null)
                    {
                        MinecraftClient.getInstance().getNetworkHandler().getConnection().disconnect(
                                new LiteralText("Disconnected by Supervisor"));
                    }
                }
                else if (buttonGroup.isSelected(forcefullyExitRadio.getModel()))
                {
                    Supervisor.logger.info("Exiting with brute force...");
                    System.exit(-1337);
                }
                else if (buttonGroup.isSelected(exitRadio.getModel()))
                {
                    Supervisor.logger.info("Exiting...");
                    MinecraftClient.getInstance().close();
                }
            }
        });

        descriptionTwo.setText("Consider these a last resort when all else fails.");

        javax.swing.GroupLayout drasticMeasuresPanelLayout = new javax.swing.GroupLayout(drasticMeasuresPanel);
        drasticMeasuresPanel.setLayout(drasticMeasuresPanelLayout);
        drasticMeasuresPanelLayout.setHorizontalGroup(
                drasticMeasuresPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(drasticMeasuresPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(drasticMeasuresPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(goButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(drasticMeasuresPanelLayout.createSequentialGroup()
                                                .addGroup(drasticMeasuresPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(disconnectRadio)
                                                        .addComponent(exitRadio)
                                                        .addComponent(forcefullyExitRadio)
                                                        .addComponent(descriptionTwo))
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        drasticMeasuresPanelLayout.setVerticalGroup(
                drasticMeasuresPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(drasticMeasuresPanelLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(descriptionTwo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(disconnectRadio)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(exitRadio)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(forcefullyExitRadio)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(goButton)
                                .addContainerGap())
        );

        buttonGroup.add(disconnectRadio);
        buttonGroup.add(exitRadio);
        buttonGroup.add(forcefullyExitRadio);

        javax.swing.GroupLayout mitigationsPanelLayout = new javax.swing.GroupLayout(mitigationsPanel);
        mitigationsPanel.setLayout(mitigationsPanelLayout);
        mitigationsPanelLayout.setHorizontalGroup(
                mitigationsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mitigationsPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(mitigationsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(mitigationsPanelLayout.createSequentialGroup()
                                                .addComponent(descriptionLabel)
                                                .addGap(0, 134, Short.MAX_VALUE))
                                        .addGroup(mitigationsPanelLayout.createSequentialGroup()
                                                .addGroup(mitigationsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(reloadPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
                                                        .addComponent(renderPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(mitigationsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(networkPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
                                                        .addComponent(drasticMeasuresPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE))))
                                .addContainerGap())
        );
        mitigationsPanelLayout.setVerticalGroup(
                mitigationsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mitigationsPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(descriptionLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(mitigationsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(renderPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(networkPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(mitigationsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(reloadPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                                        .addComponent(drasticMeasuresPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE))
                                .addContainerGap())
        );

        tabbedPane.addTab("Mitigations", mitigationsPanel);

        jMenu1.setText("File");
        menuBar.add(jMenu1);

        menuBar.add(modulesMenu);
        menuBar.add(dumpMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(tabbedPane, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(tabbedPane, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        tabbedPane.addTab("General Info", debugPanel);
        tabbedPane.addTab("Entities", entitiesPanel);
        tabbedPane.addTab("Block Entities", blockEntitiesPanel);

        // Source: https://stackoverflow.com/questions/2442599/how-to-set-jframe-to-appear-centered-regardless-of-monitor-resolution
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2 - getSize().width/2, dim.height/2 - getSize().height/2);

        pack();
    }// </editor-fold>

    public static void start()
    {
        if (instance != null)
        {
            instance.setVisible(true);
            return;
        }

        try
        {
            if (System.getProperty("java.version").contains("1.8"))
            {
                Supervisor.logger.warn("Detected version of Java that is incompatible with the modern LAF. Using the legacy LAF instead!");
                UIManager.setLookAndFeel(new MetalLookAndFeel());
                disableDarkModes = true;
            }
            else
            {
                if (W95.instance.getConfig().supervisor.useDarkMode)
                {
                    FlatMaterialDarkerIJTheme.setup();
                    //UIManager.setLookAndFeel(new FlatMaterialDarkerIJTheme());
                }
                else
                {
                    //UIManager.setLookAndFeel(new FlatMaterialLighterIJTheme());
                    FlatMaterialLighterIJTheme.setup();
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        EventQueue.invokeLater(() ->
        {
            new SupervisorFrame().setVisible(true);
            if (System.getProperty("java.version").contains("1.8"))
            {
                JOptionPane.showMessageDialog(null, "W95 has detected that you are using Java 8. Due to issues that were later discovered during testing, the modern UI has been disabled. To resolve this problem, configure your installation of Minecraft to use a newer version of Java.");
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JLabel descriptionTwo;
    private javax.swing.JCheckBox disableEntityNames;
    private javax.swing.JCheckBox disableLighting;
    private javax.swing.JRadioButton disconnectRadio;
    private javax.swing.JPanel drasticMeasuresPanel;
    private javax.swing.JButton editBlacklistButton;
    private javax.swing.JRadioButton exitRadio;
    private javax.swing.JRadioButton forcefullyExitRadio;
    private javax.swing.JButton goButton;
    private javax.swing.JScrollPane homePane;
    private javax.swing.JMenu jMenu1;
    private ModulesMenu modulesMenu;
    private DumpMenu dumpMenu;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JPanel mitigationsPanel;
    private javax.swing.JPanel networkPanel;
    private javax.swing.JCheckBox packetBlacklist;
    private javax.swing.JPanel reloadPanel;
    private javax.swing.JPanel renderPanel;
    private javax.swing.JPanel chatPanel;
    public javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JEditorPane webPage;
    private javax.swing.ButtonGroup buttonGroup;
    //
    public EntitiesPanel entitiesPanel;
    public BlockEntitiesPanel blockEntitiesPanel;
    public DebugPanel debugPanel;

    @Override
    public ActionResult onDumpEntitiesSuccessful(File location)
    {
        // Don't do anything if the Supervisor window isn't even open
        if (instance == null || !instance.isShowing())
        {
            return null;
        }

        JOptionPane.showMessageDialog(this,
                "Entity dump successful. The file is located at " + location.getName(),
                "Success", JOptionPane.INFORMATION_MESSAGE);

        return null;
    }

    @Override
    public ActionResult onDumpEntitiesFailed(Exception ex)
    {
        MutableText text;

        if (ex instanceof IllegalStateException)
        {
            text = new LiteralText(ex.getMessage());
        }
        else
        {
           text = new TranslatableText("w95.commands.entities.dump.failed");
        }

        JOptionPane.showMessageDialog(this, text.asString(),
                "Error", JOptionPane.ERROR_MESSAGE);

        return null;
    }


    /*
    @Override
    public ActionResult onSettingChanged(String id, Object cause)
    {

        if (id.equalsIgnoreCase("useDarkMode"))
        {
            try
            {
                if (W95.instance.getConfig().supervisor.useDarkMode)
                {
                    UIManager.setLookAndFeel(new FlatMaterialDarkerIJTheme());
                }
                else
                {
                    UIManager.setLookAndFeel(new FlatMaterialLighterIJTheme());
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

        return ActionResult.PASS;
    }*/
}