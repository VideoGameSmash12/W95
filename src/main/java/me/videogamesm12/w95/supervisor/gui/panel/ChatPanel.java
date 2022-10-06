package me.videogamesm12.w95.supervisor.gui.panel;

import me.videogamesm12.w95.events.ClientEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.MessageType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.UUID;

public class ChatPanel extends JPanel implements /*ClientEvents.ChatReceiveEvent*/ ClientEvents.ChatAddToHUDEvent
{
    public JLabel label = new JLabel("Message:");
    public JTextArea chat = new JTextArea();
    public JButton send = new JButton("Send");
    public JScrollPane scroll = new JScrollPane();
    public JTextField cmd = new JTextField();
    //
    public GroupLayout layout = new GroupLayout(this);
    //
    public PanelKeyListener keyListener = new PanelKeyListener();

    public ChatPanel()
    {
        chat.setEditable(false);
        chat.setColumns(20);
        chat.setRows(5);
        chat.setFont(new Font("Courier New", Font.PLAIN, 12));

        scroll.setViewportView(chat);

        send.addActionListener(this::send);
        //send.addKeyListener(keyListener);

        cmd.addKeyListener(keyListener);

        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(scroll, GroupLayout.DEFAULT_SIZE, 762, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(label)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmd)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(send, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)))
                            .addContainerGap()
                )
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap()
                        .addComponent(scroll, GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(send)
                            .addComponent(cmd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(label))
                        .addContainerGap())
        );

        Dimension labelDim = new Dimension(label.getPreferredSize().width, send.getPreferredSize().height);
        Dimension cmdDim = new Dimension(cmd.getPreferredSize().width, send.getPreferredSize().height);
        cmd.setPreferredSize(cmdDim);
        label.setPreferredSize(labelDim);

        //ClientEvents.CHAT_RECEIVE.register(this);
        ClientEvents.CHAT_ADD_MESSAGE.register(this);
    }

    public void send(ActionEvent e)
    {
        if (MinecraftClient.getInstance().player == null)
        {
            chat.append("Not currently connected to a server." + "\n");
            return;
        }

        if (e.getID() == ActionEvent.ACTION_PERFORMED)
        {
            MinecraftClient.getInstance().player.sendChatMessage(cmd.getText());
        }
    }

    public void send(KeyEvent e)
    {
        if (MinecraftClient.getInstance().player == null)
        {
            chat.append("Not currently connected to a server." + "\n");
            return;
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            MinecraftClient.getInstance().player.sendChatMessage(cmd.getText());
        }
    }

    /*@Override
    public ActionResult onChatReceive(GameMessageS2CPacket message)
    {
        System.out.println("debug shit");
        if (!message.isNonChat() && !message.getSender().equals(new UUID(0L, 0L)))
        {
            chat.append(message.getMessage().getString() + "\n");
        }
        //
        return ActionResult.PASS;
    }*/

    @Override
    public ActionResult onAddToHUD(MessageType type, Text message, UUID sender, CallbackInfo ci)
    {
        //System.out.println("debug shit");
        //System.out.println("sender " + sender);
        //if (MinecraftClient.getInstance().player != null && !sender.equals(MinecraftClient.getInstance().player.getUuid()))
        //{
        chat.append(message.getString() + "\n");
        //}
        //
        return ActionResult.PASS;
    }

    public class PanelKeyListener implements KeyListener
    {
        @Override
        public void keyTyped(KeyEvent e)
        {
            // Do nothing
        }

        @Override
        public void keyPressed(KeyEvent e)
        {
            if (e.getKeyCode() == KeyEvent.VK_ENTER)
            {
                send(e);
            }
        }

        @Override
        public void keyReleased(KeyEvent e)
        {
            // Do nothing
        }
    }
}
