package lieb;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Shape;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.DrawingSupplier;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.Rotation;

import com.fazecast.jSerialComm.SerialPort;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import java.awt.Toolkit;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;

public class Interface {

	private static JFrame frmEatsmartUserinterface;
	static SerialPort chosenPort;
	static int NumberOfForkies=0;
	static double AverageTimeValue=0.0;
	static double sum=0.0;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String [] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interface window = new Interface();
					window.frmEatsmartUserinterface.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	
	/**
	 * Create the application.
	 */
	public Interface() {
		initialize();
	}

	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmEatsmartUserinterface = new JFrame();
		frmEatsmartUserinterface.setIconImage(Toolkit.getDefaultToolkit().getImage(Interface.class.getResource("/icon.png")));
		frmEatsmartUserinterface.setResizable(false);
		frmEatsmartUserinterface.getContentPane().setForeground(Color.WHITE);
		frmEatsmartUserinterface.setTitle("EatSmart UserInterface ");
		frmEatsmartUserinterface.setPreferredSize(new Dimension(3840, 1440));
		frmEatsmartUserinterface.setBounds(20, 20, 1920, 1440);
		frmEatsmartUserinterface.setMinimumSize(new Dimension(3840, 1440));
		frmEatsmartUserinterface.setVisible(true);
		frmEatsmartUserinterface.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEatsmartUserinterface.getContentPane().setBackground(Color.WHITE);
		frmEatsmartUserinterface.getContentPane().setLayout(null);
		
		// Slow down warning
		JLabel label_slowDown = new JLabel("");
		Image imagem_slowdown=new ImageIcon(this.getClass().getResource("/slowdown.jpg")).getImage();
		label_slowDown.setBounds(1541, 291, 300, 218);
		frmEatsmartUserinterface.getContentPane().add(label_slowDown);
		label_slowDown.setIcon(new ImageIcon(imagem_slowdown));
		label_slowDown.setVisible(true);
		
		JLabel label_slowDown2 = new JLabel("");
		Image imagem_slowdown2=new ImageIcon(this.getClass().getResource("/warning.png")).getImage();
		label_slowDown2.setIcon(new ImageIcon(imagem_slowdown2));
		label_slowDown2.setBounds(1557, 48, 292, 241);
		frmEatsmartUserinterface.getContentPane().add(label_slowDown2);
		label_slowDown2.setVisible(true);
		
		
		
		// Tempo
		//Label
		JLabel lblTimeElapsed = new JLabel("Time elapsed");
		lblTimeElapsed.setFont(new Font("Trebuchet MS", Font.PLAIN, 50));
		lblTimeElapsed.setBounds(2057, 291, 322, 74);
		frmEatsmartUserinterface.getContentPane().add(lblTimeElapsed);
		//Imagem
		JLabel clock = new JLabel("");
		clock.setBounds(1951, 394, 545, 545);
		Image imagem_1=new ImageIcon(this.getClass().getResource("/time-left (1).png")).getImage();
		clock.setIcon(new ImageIcon(imagem_1));
		frmEatsmartUserinterface.getContentPane().add(clock);
		//Valor
		JLabel lblTime = new JLabel("");
		lblTime.setFont(new Font("Trebuchet MS", Font.PLAIN, 50));
		lblTime.setBounds(2104, 1019, 197, 59);
		frmEatsmartUserinterface.getContentPane().add(lblTime);
		
		
		//Conecção arduino - Botão
		JButton connectButton =new JButton("Start Meal");
		connectButton.setFont(new Font("Trebuchet MS", Font.PLAIN, 50));
		connectButton.setBounds(31,33,364,164);
		connectButton.setPreferredSize(new Dimension(364,164));
		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
			//Tempo
			 Timer t=new Timer(1000,new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
					 SimpleDateFormat sdf =new SimpleDateFormat("HH:mm:ss");
					 lblTime.setText(sdf.format(new java.util.Date()));
					 }
				 });
			 t.start(); 
			}
				});
		JPanel ConnectionPanel =new JPanel();
		ConnectionPanel.add(connectButton);
		ConnectionPanel.setBounds(0, 33, 582, 180);
		ConnectionPanel.setForeground(Color.WHITE);
		ConnectionPanel.setBackground(Color.WHITE);
		frmEatsmartUserinterface.getContentPane().add(ConnectionPanel,BorderLayout.NORTH);
		
		
		
		//Conecção - arduino 
		JComboBox<String> portList=new JComboBox<String>();
		portList.setFont(new Font("Trebuchet MS", Font.PLAIN, 50));
		ConnectionPanel.add(portList);
		
		
		// Portas arduino
		SerialPort[] portNames = SerialPort.getCommPorts();
		for (int i=0; i< portNames.length; i++)
			portList.addItem(portNames[i].getSystemPortName());
		Image imagem=new ImageIcon(this.getClass().getResource("/graphic-progression (1).png")).getImage();
		
		
						
		// Tempo médio entre garfadas
		//Label
		JLabel lblMedium = new JLabel("Mean Time Between Forks");
		lblMedium.setFont(new Font("Trebuchet MS", Font.PLAIN, 50));
		lblMedium.setBounds(2589, 261, 582, 134);
		frmEatsmartUserinterface.getContentPane().add(lblMedium);
		// Valor
		JLabel AverageTime = new JLabel("0.0");
		AverageTime.setFont(new Font("Trebuchet MS", Font.PLAIN, 50));
		AverageTime.setBounds(2834, 1019, 214, 59);
		frmEatsmartUserinterface.getContentPane().add(AverageTime);
		frmEatsmartUserinterface.setBounds(100, 100, 1331, 734);
		// Imagem
		JLabel hourglass = new JLabel("");
		hourglass.setBounds(2603, 412, 512, 527);
		Image imagem_5=new ImageIcon(this.getClass().getResource("/hourglass (2).png")).getImage();
		frmEatsmartUserinterface.getContentPane().setLayout(null);
		hourglass.setIcon(new ImageIcon(imagem_5));
		frmEatsmartUserinterface.getContentPane().add(hourglass);
		
		
		
		// Número de garfadas
		JLabel lblNumberOf = new JLabel("Number of Forks");
		lblNumberOf.setFont(new Font("Trebuchet MS", Font.PLAIN, 50));
		lblNumberOf.setBounds(3320, 276, 502, 105);
		frmEatsmartUserinterface.getContentPane().add(lblNumberOf);
		// Valor
		JLabel Forks = new JLabel();
		Forks.setText("0");
		Forks.setFont(new Font("Trebuchet MS", Font.PLAIN, 50));
		Forks.setBounds(3513, 1029, 138, 39);
		frmEatsmartUserinterface.getContentPane().add(Forks);
		// Imagem 
		JLabel fork = new JLabel("");
		fork.setBounds(3212, 409, 546, 545);
		Image imagem_2=new ImageIcon(this.getClass().getResource("/fork (1).png")).getImage();
		fork.setIcon(new ImageIcon(imagem_2));
		frmEatsmartUserinterface.getContentPane().add(fork);


		
		// Gráfico de progresso
		XYSeries series = new XYSeries("");
		XYSeriesCollection dataset = new XYSeriesCollection(series);
		JFreeChart progresschart = ChartFactory.createXYLineChart("Progress during Meal", "Numer of Forkies", "Time (seconds)",dataset, PlotOrientation.VERTICAL, true,true,false);


		Font titleFont = new Font("Trebuchet MS", Font.PLAIN, 50);
		
		// Painel com o gráfico - personalização
		JPanel ChartProgress =new JPanel();
		ChartProgress.setBackground(Color.WHITE);  // Background Color
		ChartProgress.setBounds(60, 220, 1600, 1000); //Boundaries
		ChartProgress.setPreferredSize(new Dimension(1600, 1000));
		
		
		ChartPanel chartPanel = new ChartPanel(progresschart); //Adicionar gráfico ao painel
		chartPanel.setBackground(Color.WHITE); // Background Color do painel
		chartPanel.setPreferredSize(new Dimension(1600, 1000)); // Tamanho do painel
		chartPanel.setBounds(60, 220, 1600, 1000); 
		
		ChartProgress.setBackground(Color.WHITE);
		progresschart.getPlot().setBackgroundPaint(Color.WHITE); // Plot Background Color
		Image imagem_fundo = new ImageIcon(this.getClass().getResource("/iconfundo.png")).getImage();
		progresschart.getPlot().setBackgroundImage(imagem_fundo);
		progresschart.getPlot().setOutlineVisible(false);

		
		ValueAxis domain_2=(ValueAxis) progresschart.getXYPlot().getDomainAxis();
		ValueAxis domain=(ValueAxis) progresschart.getXYPlot().getRangeAxis();
		domain_2.setTickLabelFont(new Font("Trebuchet MS", Font.PLAIN, 35));
		domain.setTickLabelFont(new Font("Trebuchet MS", Font.PLAIN, 35));
		domain.setLabelFont(new Font("Trebuchet MS", Font.PLAIN, 35));
		
		NumberAxis xAxis  = new NumberAxis();
		xAxis.setTickLabelFont(new Font("Trebuchet MS", Font.PLAIN, 35));
		xAxis.setTickUnit(new NumberTickUnit(1));
		xAxis.setLabelFont(new Font("Trebuchet MS", Font.PLAIN, 35));
		xAxis.setLabel("Number of Forkies");
		
		NumberAxis yAxis = new NumberAxis();
		yAxis.setTickLabelFont(new Font("Trebuchet MS", Font.PLAIN, 35));
		yAxis.setAutoRange(true);
		yAxis.setLabelFont(new Font("Trebuchet MS", Font.PLAIN, 35));
		yAxis.setLabel("Time (seconds)");
		
		
		progresschart.getXYPlot().setRangeAxis(yAxis);
		progresschart.getXYPlot().setDomainAxis(xAxis);
		
		//Espessura da linha do gráfico
		XYPlot plot=progresschart.getXYPlot();
		int seriesCount= plot.getSeriesCount();
		for (int i=0;i<seriesCount;i++) {
			plot.getRenderer().setSeriesStroke(i,new BasicStroke(5));
		}
		
		//Tipo de fonte do titulo
		progresschart.getTitle().setFont(titleFont);

		progresschart.getLegend().setVisible(false);
		progresschart.setBackgroundPaint(Color.WHITE); //Background Color 
		

		ChartProgress.add(chartPanel); //Adicionar o painel de gráfico ao painel da JFrame
		
		JLabel label = new JLabel("");
		chartPanel.add(label);
		
		frmEatsmartUserinterface.getContentPane().add(ChartProgress, BorderLayout.CENTER);
		

		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(connectButton.getText().equals("Start Meal")) {
					//attempt to connect to the serial port
					chosenPort=SerialPort.getCommPort(portList.getSelectedItem().toString());
					chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
					if(chosenPort.openPort()) {
						connectButton.setText("End Meal");
						portList.setEnabled(false);
					}
					// collect data from arduino
					
					Thread thread =new Thread() {
						@Override public void run() {
						Scanner scanner_1 = new Scanner(chosenPort.getInputStream());
						while(scanner_1.hasNextLine()) {
							try {
								String line = scanner_1.nextLine();
								int ElapsedTime=Integer.parseInt(line);
								series.add(NumberOfForkies++,ElapsedTime);
								if (ElapsedTime<10) {
									label_slowDown.setVisible(true);
									label_slowDown2.setVisible(true);
								}
								else {
									label_slowDown.setVisible(false);
									label_slowDown2.setVisible(false);
								}
								sum=sum+ElapsedTime;
								AverageTimeValue=sum/NumberOfForkies;
								AverageTime.setText(String.valueOf(AverageTimeValue));
								Forks.setText(String.valueOf(NumberOfForkies));
								frmEatsmartUserinterface.repaint();
							}catch(Exception e) {}
							}
						scanner_1.close();
					}
					}; 
					thread.start();
				}else {
					//disconnect from the serial port
					chosenPort.closePort();
					portList.setEnabled(true);
					connectButton.setText("Start Meal");
					series.clear();
					NumberOfForkies=0;
				}
		}
	});
				
			
		// Exit Button - Fechar a aplicação
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setFont(new Font("Trebuchet MS", Font.PLAIN, 50));
		btnExit.setBounds(3439, 1173, 364, 164);
		frmEatsmartUserinterface.getContentPane().add(btnExit);
		
		
		//Title
		JLabel lblEatSmartUser = new JLabel("EAT SMART USER INTERFACE");
		lblEatSmartUser.setForeground(Color.DARK_GRAY);
		lblEatSmartUser.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 92));
		lblEatSmartUser.setBounds(2246, 33, 1324, 184);
		frmEatsmartUserinterface.getContentPane().add(lblEatSmartUser);
		
		
		//Imagem do icone
		JLabel label_icone = new JLabel("");
		Image imagem_6=new ImageIcon(this.getClass().getResource("/icon2.png")).getImage();
		label_icone.setIcon(new ImageIcon(imagem_6));
		label_icone.setBounds(3513, 0, 232, 231);
		frmEatsmartUserinterface.getContentPane().add(label_icone);
		
		
		//Janela help
		JButton btn_help = new JButton("Help");
		btn_help.setFont(new Font("Trebuchet MS", Font.PLAIN, 50));
		btn_help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frmhelp = new JFrame();
				frmhelp.setTitle("Help - EatSmart User Interface");
				frmhelp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frmhelp.setMinimumSize(new Dimension(1920, 1440));
				frmhelp.getContentPane().setLayout(null);
				frmhelp.setResizable(false);
				frmhelp.setIconImage(Toolkit.getDefaultToolkit().getImage(Interface.class.getResource("/icon.png")));
				
				//Título
				JLabel label_help_title=new JLabel("Help - EatSmart User Interface");
				label_help_title.setBounds(570,10,1920,100);
				label_help_title.setFont(new Font("Trebuchet MS", Font.BOLD, 50));
				
				//Texto
				JLabel help_text = new  JLabel("<html>"+"Welcome to EatSmart!" + "<br />" + "<br />"+ " Here you can find a quick explanation of the user interface. Whenever you press \"Start Meal\", your meal will automatically start being tracked with the start of the clock and the count of the forks. The time spent between forks is ploted in the chart presented in the left. Regarding this time, if you eat too fast (i.e., if you spend less than 10 seconds between forks), a warning will appear, suggesting to slow down; otherwise, nothing will happen and the tracking will continue. " + "<br />" + "During your meal, the average time between forks is shown too. " + "<br />" + "At the end of the meal, just press \"EndMeal\", and don't forget to clean the fork, of course! " + "<br />" +"<br />" +"<br />" +"<br />" + "Thank you so much for using our product and Bon Appétit!"+"<html>"); 
				help_text.setFont(new Font("Trebuchet MS", Font.PLAIN, 45));
				help_text.setOpaque(false);
				help_text.setVisible(true);
				help_text.setBounds(100, 100, 1700, 1200);
				help_text.setPreferredSize(new Dimension(1700,1200));
				frmhelp.getContentPane().add(label_help_title);
				frmhelp.getContentPane().add(help_text);
				
				//Botão exit 
				JButton btnExit_2 = new JButton("Exit");
				btnExit_2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						frmhelp.setVisible(false);
					}
				});
				btnExit_2.setFont(new Font("Trebuchet MS", Font.PLAIN, 50));
				btnExit_2.setBounds(1500, 1150, 364, 164);
				frmhelp.getContentPane().add(btnExit_2);
				
				//icon app
				JLabel lbl_icon = new JLabel("");
				Image help_icon2=new ImageIcon(this.getClass().getResource("/icon2.png")).getImage();
				lbl_icon.setIcon(new ImageIcon(help_icon2));
				lbl_icon.setBounds(1280, 10, 256, 256);
				frmhelp.getContentPane().add(lbl_icon);
				
				
				//icon help
				JLabel lbl_helpicon = new JLabel("");
				Image help_icon=new ImageIcon(this.getClass().getResource("/question.png")).getImage();
				lbl_helpicon.setIcon(new ImageIcon(help_icon));
				lbl_helpicon.setBounds(1500, 10, 256, 256);
				frmhelp.getContentPane().add(lbl_helpicon);
			
				frmhelp.getContentPane().setBackground(Color.WHITE);
				frmhelp.setVisible(true);
			}
		});
		btn_help.setBounds(1755, 1173, 364, 164);
		frmEatsmartUserinterface.getContentPane().add(btn_help);		
		
	}	
}
