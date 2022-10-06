package me.videogamesm12.w95.supervisor.gui.panel;

import org.apache.commons.net.telnet.TelnetClient;

import javax.swing.*;

public class TelnetPanel extends JPanel
{
    public TelnetClient client = new TelnetClient();
    //--
    public JTextPane output = new JTextPane();
    public JScrollPane scroll = new JScrollPane();

    public TelnetPanel()
    {

    }
}
