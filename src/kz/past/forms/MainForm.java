package kz.past.forms;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RepaintManager;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import com.toedter.calendar.JCalendar;

import kz.past.actions.ActionImportCSV;
import kz.past.helpers.AppSettings;
import kz.past.helpers.Conn;
import kz.past.helpers.DBFunctions;
import kz.past.helpers.ParserCSV;
import kz.past.helpers.ParserXLSX;
import kz.past.helpers.tablemodels.LastReportTableDataModel;
import kz.past.helpers.tablemodels.LastViewTableDataModel;


public class MainForm extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Компоненты Формы клиента
	 private final String TXT_DATE = AppSettings.getKey("TXT_DATE");
	 private final String TXT_TIME = AppSettings.getKey("TXT_TIME");
	 private final String TXT_CLIENT_NAME = AppSettings.getKey("TXT_CLIENT_NAME");
	 private final String TXT_PHONE = AppSettings.getKey("TXT_PHONE");
	 private final String TXT_CITY = AppSettings.getKey("TXT_CITY");
	 private final String TXT_PROC = AppSettings.getKey("TXT_PROC");
	 private final String TXT_PROC_TYPE = AppSettings.getKey("TXT_PROC_TYPE");
	 private final String TXT_MASTER = AppSettings.getKey("TXT_MASTER");
	 private final String TXT_MIX = AppSettings.getKey("TXT_MIX");
	 private final String TXT_COMMENT = AppSettings.getKey("TXT_COMMENT");
	 
	
	 		JLabel lblDate = new JLabel(TXT_DATE);
	 			JTextField tfDate = new JTextField("");
			JLabel lblTime = new JLabel(TXT_TIME);	
				JTextField tfTime = new JTextField("");
			JLabel lblClientName = new JLabel(TXT_CLIENT_NAME);
				JTextField tfClientName = new JTextField("");
			JLabel lblPhone = new JLabel(TXT_PHONE);
				JTextField tfPhone = new JTextField("");
			JLabel lblCity = new JLabel(TXT_CITY);
				JTextField tfCity = new JTextField("");
			JLabel lblProc = new JLabel(TXT_PROC);
				JTextField tfProc = new JTextField("");
			JLabel lblProcType = new JLabel(TXT_PROC_TYPE);
				JTextField tfProcType = new JTextField("");
			JLabel lblMaster = new JLabel(TXT_MASTER);
				JTextField tfMaster = new JTextField("");
			JLabel lblMix = new JLabel(TXT_MIX);
				JTextField tfMix = new JTextField("");
			JLabel lblComment = new JLabel(TXT_COMMENT);
				JTextArea taComment = new JTextArea(20, 20);
			JLabel lblImg = new JLabel();
				
						
	
	
	JButton btnImportExcelToBD = new JButton("Загрузить в БД");
	JFileChooser fileCh = new JFileChooser();
	FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel", "xlsx");
	FileNameExtensionFilter filterCSV = new FileNameExtensionFilter("CSV", "csv");
	
	JTable tblReport;
	DefaultTableModel dtm =  new DefaultTableModel();
	JMenuBar menuBar = new JMenuBar();
	JSplitPane sp = null;
	JPanel panelClient = null;
	JScrollPane sPane = null; //ScrollPane for data table 
	JLabel lblHdrReport = new JLabel("");
	LastViewTableDataModel lvtdm = null; // Модель таблицы для просмотра клиентов
	
	String[] defaultHeader = {"Дата", "Время","Имя","Телефон","Процедура","Тип", "Мастер", "$", "Примечание", "Коррекция", "Город"};
	
	Object[][] testData = new String[][]{
			{"02.05.14",	"9:30",	"Надежда Коваленко",	"87013745220",	"брови",	"перв.",	"Мария Зеленко",	"7500,00", "---", "+++", "Актау"},
			{"03.05.14",	"9:50",	"Асыл Байсырова",	"87756201011",	"брови",	"перв.",	"Мария Зеленко",	"500,00", "444444", "----", "Астана"}
	};
	
	
	JCalendar jCalStart = null;
	JCalendar jCalEnd = null;
	
	////////////////////////////////////
	// Constructor of application form//
	////////////////////////////////////
	public MainForm() {
	
		setSize(900,650);
		setResizable(false);
		RepaintManager.currentManager(this).setDoubleBufferingEnabled(true);
		setTitle("Учет клиентов. Студия Евгении Пастушковой");
		setLocationRelativeTo(null);
		setJMenuBar(createMenu());	   //  CreateMenu() редактировать
		    panelClient = getClientForm(true); // Get client form
		   			panelClient.setBounds(7, 7, 870, 250);
		   JPanel panelData = new JPanel();
		   		  panelData.setLayout(null);
		    sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT,panelClient, panelData);
		    sp.setDividerLocation(250);
		    sp.setDividerSize(2);
		
			dtm.setDataVector(testData, defaultHeader);
		
			fileCh.setFileFilter(filter);
			
		 tblReport = new JTable(dtm);
		 	tblReport.getColumnModel().getColumn(0).setPreferredWidth(60);
		 	tblReport.getColumnModel().getColumn(1).setPreferredWidth(37);
		 	tblReport.getColumnModel().getColumn(2).setPreferredWidth(130);
		 	tblReport.getColumnModel().getColumn(5).setPreferredWidth(35);
		 	tblReport.getColumnModel().getColumn(6).setPreferredWidth(130);
		 	tblReport.getColumnModel().getColumn(7).setPreferredWidth(40);
		 	tblReport.getColumnModel().getColumn(8).setPreferredWidth(40);
		 	tblReport.addMouseListener(new MouselListenerEditClientInfoTable());
		 	
		   sPane = new JScrollPane(tblReport);
		 //tblReport.setBounds(7, 7, 870, 330);
			sPane.setBounds(7, 7, 870, 330);
		panelData.add(sPane);	
		panelData.add(lblHdrReport);
			getContentPane().add(sp);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	//MENU OF PROGRAMM  //////////////////////////
	private JMenuBar createMenu(){
		JMenuBar menuBar = new JMenuBar();
		JMenu view = new JMenu("Просмотр");
//				JMenu chMasters = new JMenu("Просмотр для ...");
//				view.add(chMasters);
//					JCheckBoxMenuItem chAll = new JCheckBoxMenuItem("Для всех");
//					chMasters.add(chAll);
//					 HashMap<Integer, String> hmMasters = (HashMap) DBFunctions.getMasters(Conn.getConnection());
//					 	Iterator itr = hmMasters.keySet().iterator();
//					 		while(itr.hasNext()){
//					 			chMasters.add(new JCheckBoxMenuItem(hmMasters.get(itr.next()))) ;
//					 			
//					 		}
				JMenuItem lastDataview = new JMenuItem("Последний месяц");
					lastDataview.addActionListener(new ActionListenerGetLastView(null, null));
					view.add(lastDataview);
				JMenuItem customDataview = new JMenuItem("Выбранный период");
					view.add(customDataview);
					customDataview.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							new ChDateForm();
						}
					});
					
				
		JMenu file = new JMenu("Файл");
			JMenuItem importReportCSV = new JMenuItem("Импорт отчета CSV");
			importReportCSV.addActionListener( new ActionImportInnerCSV() );
			file.add(importReportCSV);
			
			JMenuItem openReport = new JMenuItem("Импорт отчета XLS");
			openReport.addActionListener(new ActionListenerAddReport());
			file.add(openReport);
			
			
			
		JMenu edit = new JMenu("Правка");
			
		JMenu setting =  new JMenu("Настройки");
			JMenuItem miCatalog = new JMenuItem("Справочники");
			miCatalog.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					new SettingForm();				
				}
			});
			setting.add(miCatalog);
			
		JMenu report = new JMenu("Отчет");
			JMenuItem lastMonthReport = new JMenuItem("Отчет за посл. месяц");
					lastMonthReport.addActionListener(new ActionListenerGetReport(null, null));
					report.add(lastMonthReport);
			JMenuItem customReport = new JMenuItem("Выборочный отчет");
					customReport.addActionListener(new ActionListener() {			
						@Override
						public void actionPerformed(ActionEvent e) {
							new СhDateWindowReport();
						}
					});
					report.add(customReport);
					
		JMenuItem exit = new JMenuItem("Выход");
				exit.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						//Thread.currentThread();
						System.exit(0);
						
					}
				});
				
		menuBar.add(view);
		menuBar.add(file);
		menuBar.add(edit);
		menuBar.add(setting);
		menuBar.add(report);
		menuBar.add(exit);
		
		return menuBar;
	}
	
	//PANEL OF CLIENT   ///////////////////////////////
	private JPanel getClientForm(boolean editable){
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(BorderFactory.createTitledBorder("Клиент"));
			lblDate.setBounds(20, 15, 100, 30);		
				panel.add(lblDate);	
			tfDate.setBounds(20, 40, 100, 30);
				tfDate.setEditable(editable);
				panel.add(tfDate);
			lblClientName.setBounds(20, 70, 100, 30);
				panel.add(lblClientName);				
			tfClientName.setBounds(20, 95, 150, 30);
				tfClientName.setEditable(editable);
				panel.add(tfClientName);
			lblPhone.setBounds(20, 125, 100, 30);
				panel.add(lblPhone);
			tfPhone.setBounds(20, 150, 150, 30);
				tfPhone.setEditable(editable);
				panel.add(tfPhone);
			lblMix.setBounds(20, 180, 300, 30);
				panel.add(lblMix);
			tfMix.setBounds(20, 205, 350, 30);
				tfMix.setEditable(editable);
				panel.add(tfMix);
				
			lblTime.setBounds(140, 15, 100, 30);
				panel.add(lblTime);	
			tfTime.setBounds(140, 40, 100, 30);
				tfTime.setEditable(editable);
				panel.add(tfTime);
			lblProc.setBounds(190, 70, 100, 30);
				panel.add(lblProc);	
			tfProc.setBounds(190, 95, 100, 30);
				tfProc.setEditable(editable);
				panel.add(tfProc);
			lblProcType.setBounds(190, 125, 300, 30);
				panel.add(lblProcType);
			tfProcType.setBounds(190, 150, 100, 30);
				tfProcType.setEditable(editable);
				panel.add(tfProcType);

			lblComment.setBounds(310, 70, 100, 30);
				panel.add(lblComment);
			taComment.setBounds(310, 95, 300, 90);
				taComment.setEditable(editable);
				panel.add(taComment);
				
			lblMaster.setBounds(540,15,100,30);
				panel.add(lblMaster);
			tfMaster.setBounds(590, 15, 150,30);
				tfMaster.setEditable(editable);
				panel.add(tfMaster);
			lblCity.setBounds(760, 15, 100, 30);
				panel.add(lblCity);
			tfCity.setBounds(780, 15, 90,30);
				tfCity.setEditable(editable);
				panel.add(tfCity);
				
			lblImg.setBounds(630, 60, 240,150);				//Image img = new ImageIcon(logo.png);
			lblImg.setBorder(BorderFactory.createEtchedBorder());
			lblImg.setHorizontalAlignment(SwingConstants.CENTER);
			//Image panel
			ImageIcon img = new ImageIcon("logo.png");
			lblImg.setIcon(img);
			lblImg.setAlignmentX(RIGHT_ALIGNMENT);
             panel.add(lblImg);
			
            btnImportExcelToBD.setBounds(730, 213, 140, 30);
            btnImportExcelToBD.setText("<html><font color='red'><b>Загрузить в БД</b></font>");
            btnImportExcelToBD.setVisible(false);
            btnImportExcelToBD.addActionListener(new ActionListenerAddDataToBD());
            	panel.add(btnImportExcelToBD);
		
		return panel;
	}
	
	
/////////////////////////////////
//// IMPORT CSV file into Table//
////////////////////////////////	
	/**
	 * ActionListener class
	 * for import CSV file
	 * @author mikhailpastushkov
	 *
	 */
  class ActionImportInnerCSV implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
					fileCh.setFileFilter(filterCSV); 
					Vector vHeader = new Vector<String>();
					vHeader.addAll(Arrays.asList(defaultHeader));
					int res = fileCh.showOpenDialog(null);
					
					if ( res == JFileChooser.APPROVE_OPTION ){
							System.out.println("Choosen file is "+fileCh.getSelectedFile());
						 ParserCSV pCSV = new ParserCSV(fileCh.getSelectedFile());
						 
						 dtm.setDataVector(pCSV.getvTableData(), vHeader );
						 tblReport.setModel(dtm);
						 new JOptionPane().showMessageDialog(null, "Количество импортируемых записей: "+pCSV.getvTableData().size()+" шт.");
							btnImportExcelToBD.setVisible(true);
					}	
					System.out.println("Import CSV file into table.");
		}

	}

	
	
	
	//ACTION: ADD REPORT ///////////////////////////
	class ActionListenerAddReport implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			int returnVal = fileCh.showOpenDialog(MainForm.this);
			if(returnVal == JFileChooser.APPROVE_OPTION){
				System.out.println("// Report file - "+fileCh.getSelectedFile());
				ParserXLSX parser  = new ParserXLSX(fileCh.getSelectedFile().toString());
				
				Vector vHeader = new Vector(); // Имена столбцов таблицы
					vHeader.addAll(Arrays.asList(defaultHeader));
					
//				 	for(Object obj : defaultHeader){
//				 		vHeader.add(obj);
//				 	}
				 
				 
				dtm.setDataVector(parser.getVTableData(), vHeader); // Загружаем данные в таблицу 
				
						// Change column width
						tblReport.getColumnModel().getColumn(0).setPreferredWidth(60);
					 	tblReport.getColumnModel().getColumn(1).setPreferredWidth(37);
					 	tblReport.getColumnModel().getColumn(2).setPreferredWidth(130);
					 	tblReport.getColumnModel().getColumn(5).setPreferredWidth(35);
					 	tblReport.getColumnModel().getColumn(6).setPreferredWidth(130);
					 	tblReport.getColumnModel().getColumn(7).setPreferredWidth(40);
					 	tblReport.getColumnModel().getColumn(8).setPreferredWidth(40);
					 	
				tblReport.setModel(dtm); 
				new JOptionPane().showMessageDialog(null, "Количество импортируемых записей: "+parser.getVTableData().size()+" шт.");
				btnImportExcelToBD.setVisible(true);
			}
			
		}
		
	}
	
	//ACTION: ADD CLIENTS TO DATABASE ////////////////////////
	class ActionListenerAddDataToBD implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {		
			DBFunctions.insertDatatoDB(tblReport.getModel());
			//JOptionPane.showMessageDialog(null, "Данные были добавлены в БД");
			btnImportExcelToBD.setVisible(false);
		}
	}
	
	//ACTION: EDIT CLIENT INFO ////////////////
	class MouselListenerEditClientInfoTable extends MouseAdapter{

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 1){
				int selectedRow = tblReport.getSelectedRow();
 				int columnCount = dtm.getColumnCount();
 				TableModel tm = tblReport.getModel();
 				for (int i = 0; i < columnCount; i++) {
					System.out.print(" "+tm.getValueAt(selectedRow, i)+" ");
				}
 				System.out.println("");	
			}
		}
	}
	
	//ACTION: GET LATEST REPORT
	class ActionListenerGetReport implements ActionListener{
			Date startDate = null;
			Date endDate = null;
			
		public ActionListenerGetReport(Date sDate, Date eDate) {
			this.startDate = sDate;
			this.endDate = eDate;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			EventQueue.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					
					sp.setDividerLocation(0);
					lblHdrReport.setVisible(true);
					panelClient.setVisible(false);
					LastReportTableDataModel lastReportTDM = new LastReportTableDataModel(true);
						lastReportTDM.setData(startDate, endDate);
						startDate = lastReportTDM.getStartDate();
						endDate = lastReportTDM.getEndDate();
					tblReport.setRowSorter(null);
					tblReport.setModel(lastReportTDM);
					// Ширина столбцов
					tblReport.getColumnModel().getColumn(0).setPreferredWidth(220);
					for(int i = 1; i<9; i++) tblReport.getColumnModel().getColumn(i).setPreferredWidth(30);
					tblReport.getColumnModel().getColumn(9).setPreferredWidth(80);
					tblReport.getColumnModel().getColumn(10).setPreferredWidth(80);
					tblReport.getColumnModel().getColumn(11).setPreferredWidth(80);
					tblReport.getColumnModel().getColumn(12).setPreferredWidth(80);
					lblHdrReport.setText("<HTML><b><h2>Отчет по клиентам за период с "+ new SimpleDateFormat("dd-MM-yyyy").format(startDate)+" по "+
															new SimpleDateFormat("dd-MM-yyyy").format(endDate));
					lblHdrReport.setBounds(150, 7, 600, 40);
					setSize(900, (tblReport.getRowCount()*35)+100);
					sPane.setBounds(7, 60, 870, tblReport.getRowCount()*20);
					
					
				}
			});
			  
		}
		
	}
	
	//ACTION: VIEW - GET LAST DATA
	class ActionListenerGetLastView implements ActionListener{
		Date startDate = null; 
		Date endDate = null;
		
		public ActionListenerGetLastView(Date startDate, Date endDate) {
			this.startDate = startDate;
			this.endDate = endDate;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			EventQueue.invokeLater(new Runnable() {	
				@Override
				public void run() {
					
					
					setVisible(false);
					setSize(900, 650);
					setVisible(true);
					sp.setDividerLocation(250);
					
					panelClient.setVisible(true);
					
					
					sp.setMaximumSize(new Dimension(250,250));
					sPane.setBounds(7, 7, 870, 330);
					lblHdrReport.setVisible(false);

					
					lvtdm = new LastViewTableDataModel();
					lvtdm.setDataSource(Conn.getConnection(), startDate, endDate);
					tblReport.setModel(lvtdm);
					tblReport.setRowSorter(new TableRowSorter(lvtdm));
				
					
					tblReport.getColumnModel().getColumn(0).setPreferredWidth(30);
					tblReport.getColumnModel().getColumn(1).setPreferredWidth(90);
					tblReport.getColumnModel().getColumn(2).setPreferredWidth(60);
					tblReport.getColumnModel().getColumn(3).setPreferredWidth(190);
					tblReport.getColumnModel().getColumn(4).setPreferredWidth(120);
					tblReport.getColumnModel().getColumn(5).setPreferredWidth(90);
					tblReport.getColumnModel().getColumn(6).setPreferredWidth(90);
					tblReport.getColumnModel().getColumn(7).setPreferredWidth(50);
					tblReport.getColumnModel().getColumn(8).setPreferredWidth(190);
					tblReport.getColumnModel().getColumn(9).setPreferredWidth(50);
					tblReport.getColumnModel().getColumn(9).setPreferredWidth(50);
					

					
		
				}
			});
			
			
			
			
		}
		
	}
	//////////////////////////////////////////////////////
	//CLASS: CHDATEFORM : Form for selecting dates
	//////////////////////////////////////////////////////
	
	public class ChDateForm extends JFrame{
		
		JButton btnOk;
		public ChDateForm() {
			jCalStart = new JCalendar(new Date());
		    jCalEnd = new JCalendar(new Date());
			
			JPanel pnlStart = new JPanel();
				 pnlStart.setBorder(BorderFactory.createTitledBorder("Дата начала:"));
				 pnlStart.setBounds(10, 5, 235, 193);
				 pnlStart.add(jCalStart);
				 
			JPanel pnlEnd = new JPanel();
				pnlEnd.setBorder(BorderFactory.createTitledBorder("Дата окончания:"));
				pnlEnd.setBounds(245, 5, 235, 193);
				pnlEnd.add(jCalEnd);
			
		        btnOk = new JButton("ОК");
		        addActionToBtn(btnOk);
				btnOk.setBounds(200,200,100,30);
			JButton btnClose = new JButton("Закрыть");
			JPanel panel = new JPanel();
				panel.setLayout(null);
				panel.add(btnOk);
				panel.add(pnlStart);
				panel.add(pnlEnd);
			setSize(495, 275);
			setAlwaysOnTop(true);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			setLocationRelativeTo(this);
			setContentPane(panel);
				
			
			
			setVisible(true);	
		}
		void addActionToBtn(JButton btnOk){
			btnOk.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println(jCalStart.getDate()+" "+ jCalEnd.getDate());
					new ActionListenerGetLastView(jCalStart.getDate(), jCalEnd.getDate()).actionPerformed(null);
					getToolkit().getSystemEventQueue().postEvent(new WindowEvent(ChDateForm.this, WindowEvent.WINDOW_CLOSING));
					
				}
			});//
		}
	}
  ////////////////////////////////////
	//CLASS СhDateWindowReport: FORM OF CHOOSING DATE FOR REPORT
	public class СhDateWindowReport extends ChDateForm{
		public СhDateWindowReport() {
			super();
		}
		void addActionToBtn(JButton btnOk){
			btnOk.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println(jCalStart.getDate()+" "+ jCalEnd.getDate());
					new ActionListenerGetReport(jCalStart.getDate(), jCalEnd.getDate()).actionPerformed(null);
					getToolkit().getSystemEventQueue().postEvent(new WindowEvent(СhDateWindowReport.this, WindowEvent.WINDOW_CLOSING));
					
				}
			});//
		}
		
	}
}
