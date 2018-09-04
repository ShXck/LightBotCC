package org.tec.comp;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import org.tec.comp.utilities.Stream_Handler;

public class IDE_Window implements Runnable, ActionListener {
	
	private JFrame frame;
	private JLabel console_lbl;
	private JTextPane coding_field;
	
	private final String WINDOW_TITLE = "LightBot IDE";
	private final String THEME_BOX_TITLE = "Lighbot IDE Theme";
	private final String MENU_BOX_TITLE = "Menu";
	private final String OPEN_FILE_LBL = "Open File";
	private final String SAVE_FILE_LBL = "Save File";
	private final String BUILD_CODE_LBL = "Build Code";
	private final String CONSOLE_LBL = "Console";

	@Override
	public void run() {
		set_components();
	}

    /**
     * Pone un mensaje en la consola.
     * @param msg el mensaje que se mostrará.
     * @param color el color del mensaje.
     */
	private void set_console_msg(String msg, Color color) {
		console_lbl.setText(msg);
		console_lbl.setForeground(color);
	}

    /**
     * Construye toda la interfaz gráfica de la ventana del IDE.
     */
	private void set_components() {
        frame = new JFrame(WINDOW_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final JPanel gui_pane = new JPanel(new BorderLayout(5,5));

        JPanel theme_box = new JPanel(
            new FlowLayout(FlowLayout.RIGHT, 3,3));
        theme_box.setBorder(
            new TitledBorder(THEME_BOX_TITLE));

        final UIManager.LookAndFeelInfo[] plafInfos =
            UIManager.getInstalledLookAndFeels();
        String[] plafNames = new String[plafInfos.length];
        for (int ii=0; ii<plafInfos.length; ii++) {
            plafNames[ii] = plafInfos[ii].getName();
        }
        final JComboBox theme_chooser = new JComboBox(plafNames);
        theme_box.add(theme_chooser);

        theme_chooser.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                int index = theme_chooser.getSelectedIndex();
                try {
                    UIManager.setLookAndFeel(
                        plafInfos[index].getClassName() );
                    SwingUtilities.updateComponentTreeUI(frame);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        } );

        gui_pane.add(theme_box, BorderLayout.NORTH);

        JPanel ide_menu = new JPanel();
        ide_menu.setBorder(
            new TitledBorder(MENU_BOX_TITLE));
        ide_menu.setLayout(new BoxLayout(ide_menu, BoxLayout.PAGE_AXIS));
        gui_pane.add(ide_menu, BorderLayout.WEST);

        JButton save_file_button = new JButton(SAVE_FILE_LBL);
        JButton open_file_button = new JButton(OPEN_FILE_LBL);
        JButton build_code_button = new JButton(BUILD_CODE_LBL);
        ide_menu.add(save_file_button);
        ide_menu.add(open_file_button);
        ide_menu.add(build_code_button);
        
        save_file_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				save_file();
				set_console_msg(Error_Msg_Handler.build_code_saved_msg(), Color.BLUE);
			}
        	
        });
        open_file_button.addActionListener(this);
        build_code_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(coding_field.getText().isEmpty()) {
					set_console_msg(Error_Msg_Handler.build_no_code_err(), Color.RED);
				} else {
					save_file();
					// Start code analysis/parsing/semantic, etc.
				}
			}      	
        });
        
        coding_field = new JTextPane();
		coding_field.setPreferredSize(new Dimension(1000,400));
		coding_field.setFocusable(true);

        JScrollPane coding_pane = new JScrollPane(coding_field);
        Dimension tablePreferred = coding_pane.getPreferredSize();
        coding_pane.setPreferredSize(
            new Dimension(tablePreferred.width, 300) );

        JPanel console = new JPanel(new GridBagLayout());
        console.setBorder(
            new TitledBorder(CONSOLE_LBL));
        
        console.setPreferredSize(new Dimension(100,150));
        
        console_lbl = new JLabel("NO MESSAGES");
        
        console.add(console_lbl, null);

        JSplitPane split_pane = new JSplitPane(
            JSplitPane.VERTICAL_SPLIT,
            coding_pane,
            new JScrollPane(console));
        gui_pane.add(split_pane, BorderLayout.CENTER);

        frame.setContentPane(gui_pane);

        frame.pack();

        frame.setLocationRelativeTo(null);
        try {
            frame.setLocationByPlatform(true);
            frame.setMinimumSize(frame.getSize());
        } catch(Throwable ignoreAndContinue) {
        }

        frame.setVisible(true);
    }

    /**
     * Abre el explorador de archivos.
     */
	private void open_file_explorer() {
		JFileChooser file_exp = new JFileChooser();
		if(file_exp.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
			coding_field.setText(Stream_Handler.get_file_content(file_exp.getSelectedFile()));
		}
	}

    /**
     * Guarda el código del IDE en un archivo de texto.
     */
	private void save_file() {
		try {
			Stream_Handler.write_file(coding_field.getText(), "mycode"); // TODO: si no se ha guardado el archivo anteriormente permitir ponerle nombre al archivo. "MYCODE" es por defecto y para pruebas.
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		open_file_explorer();
	}
}
