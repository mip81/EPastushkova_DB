package kz.past.forms.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.sql.rowset.CachedRowSet;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import kz.past.helpers.TableUtils;
import kz.past.helpers.tablemodels.MastersTableModel;

public class MastersPanel {
	//Поля для формы мастеров
			String action = "";
			 private JTable tblMasters = null;
			 private MastersTableModel mtm = null;
			
			 private JPanel panelMasters = new JPanel(new BorderLayout());
			 private JPanel panelMastersDat = new JPanel(new GridBagLayout());
			 private JScrollPane scrlPaneMas = new JScrollPane();
			 private JButton btnSaveMas = new JButton("Сохранить");
			 private JButton btnEditMas = new JButton("Редактировать");
			 private JButton btnNewMas = new JButton("Новый");
			
			 private JLabel lblId = new JLabel("ID");
			 private JTextField txtId = new JTextField();
			 private JLabel lblName = new JLabel("Имя");
			 private JTextField txtName = new JTextField();
			 private JLabel lblMName = new JLabel("Отчество");
			 private JTextField txtMName = new JTextField();
			 private JLabel lblLName = new JLabel("Фамилия");
			 private JTextField txtLName = new JTextField();
			 private JLabel lblAddress = new JLabel("Адрес");
			 private JTextField txtAddress = new JTextField();
			 private JLabel lblCellPhone = new JLabel("Сотовый");
			 private JTextField txtCellPhone = new JTextField();
			 private JLabel lblHomePhone = new JLabel("Домашний");
			 private JTextField txtHomePhone = new JTextField();
			 private JLabel lblSalary = new JLabel("Оклад (%=0)");
			 private JTextField txtSalary = new JTextField();
			 private JLabel lblTypeContract = new JLabel("Тип к.");
			 private JTextField txtTypeContract = new JTextField();
			 private JLabel lblSDateContract = new JLabel("Дата конт.(Нач.)");
			 private JFormattedTextField txtSDateContract = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
			 private JLabel lblEDateContract = new JLabel("Дата конт.(Кон.)");
			 private JFormattedTextField txtEDateContract = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
			 private JLabel lblLocation = new JLabel("Город");
			 private JTextField txtLocation = new JTextField();
	
	public JPanel getPanel(){
		JPanel panel = new JPanel(new BorderLayout());
		
		mtm = new MastersTableModel();
		tblMasters = new JTable(mtm);
		
		
		
		
	// Change the widths of column
		TableUtils.setWidthForTableReport(tblMasters);
		
		scrlPaneMas.setViewportView(tblMasters);
		scrlPaneMas.setPreferredSize(new Dimension(900, 160)); //180
		
		scrlPaneMas.setEnabled(true);
		
		// Панель для редактирования мастеров
		int gridx = 0;
		int gridy = 0;
		txtId.setEditable(false);
		panelMastersDat.setPreferredSize(new Dimension(900, 50));
		GridBagConstraints cbc = new GridBagConstraints();
		cbc.fill = GridBagConstraints.HORIZONTAL;
		cbc.insets =new Insets(0, 5, 0, 5);
		cbc.weightx = 0.5;
		cbc.gridx = gridx;
		cbc.gridy = 0;
		panelMastersDat.add(lblId, cbc);
		cbc.gridy = 1;
		panelMastersDat.add(txtId, cbc);
		cbc.gridy = 0;;
		cbc.gridx = ++gridx;
		cbc.ipadx = 50; 
		panelMastersDat.add(lblName, cbc);
		cbc.gridy = 1;
		panelMastersDat.add(txtName, cbc);
		cbc.gridy = 0;
		cbc.gridx = ++gridx;
		cbc.ipadx = 30;
		panelMastersDat.add(lblMName, cbc);
		cbc.gridy = 1;
		panelMastersDat.add(txtMName, cbc);
		cbc.gridy = 0;
		cbc.gridx = ++gridx;
		cbc.ipadx = 30;
		panelMastersDat.add(lblLName, cbc);
		cbc.gridy = 1;
		panelMastersDat.add(txtLName, cbc);
		cbc.gridy = 0;
		cbc.gridx = ++gridx;
		cbc.ipadx = 0;
		cbc.ipadx = 40;
		panelMastersDat.add(lblCellPhone, cbc);
		cbc.gridy = 1;
		cbc.ipadx = 40;
		panelMastersDat.add(txtCellPhone, cbc);
		cbc.gridy = 0;
		cbc.gridx = ++gridx;
		panelMastersDat.add(lblHomePhone, cbc);
		cbc.gridy = 1;
		panelMastersDat.add(txtHomePhone, cbc);
		cbc.gridy = 2;
		cbc.gridx = 1;
		panelMastersDat.add(lblSalary, cbc);
		cbc.gridy = 3;
		cbc.gridx = 1;
		cbc.ipadx = 40;
		panelMastersDat.add(txtSalary, cbc);
		cbc.gridy = 2;
		cbc.gridx = 2;
		cbc.ipadx = 0;	
		panelMastersDat.add(lblTypeContract, cbc);
		cbc.gridy = 3;
		cbc.gridx = 2;
		panelMastersDat.add(txtTypeContract, cbc);
		txtTypeContract.setToolTipText("Тип контракта (1=оклад, 2=%)");
		cbc.gridy = 2;
		cbc.gridx = 3;
		panelMastersDat.add(lblSDateContract, cbc);
		cbc.gridy = 3;
		cbc.gridx = 3;
		panelMastersDat.add(txtSDateContract, cbc);
		txtSDateContract.setToolTipText("Дата подписания договора. Формат YYYY-DD-MM");
		cbc.gridy = 2;
		cbc.gridx = 4;
		panelMastersDat.add(lblEDateContract, cbc);
		cbc.gridy = 3;
		cbc.gridx = 4;
		panelMastersDat.add(txtEDateContract, cbc);
		txtEDateContract.setToolTipText("Дата окончания договора. Формат YYYY-DD-MM");
		cbc.gridy = 2;
		cbc.gridx = 5;
		panelMastersDat.add(lblLocation, cbc);
		cbc.gridy = 3;
		cbc.gridx = 5;
		panelMastersDat.add(txtLocation, cbc);
		txtLocation.setToolTipText("Город");
		
		cbc.gridy = 3;
		cbc.insets =new Insets(0, 10, 0, 0);
		cbc.gridx = 7;
		panelMastersDat.add(btnNewMas, cbc);
		cbc.gridx = 8;
		panelMastersDat.add(btnEditMas,cbc);
		cbc.gridx = 9;
		panelMastersDat.add(btnSaveMas, cbc);
		
		panelMastersDat.setPreferredSize(new Dimension(900,120));
		
		// Adding listeners to buttons "SAVE", "EDIT" and "UPDATE"
		btnNewMas.addActionListener(new ActLstNewMaster());
		btnEditMas.addActionListener(new ActLstUpdateMaster());
		btnSaveMas.addActionListener(new ActLstSaveDataMasters());
		
		panelMasters.add(scrlPaneMas, BorderLayout.NORTH);
		panelMasters.add(panelMastersDat, BorderLayout.SOUTH);
		
		setTxtEditable(false, true);
		
		
//////////////////////////////////////////////////////////
// Set data in the form when you click on the row of table
//////////////////////////////////////////////////////////
		tblMasters.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				HashMap hmRowData = new HashMap();
				String strData = "";

				if (e.getClickCount()==1){
					int row = tblMasters.getSelectedRow();
					for (int col = 0; col < mtm.getColumnCount(); col++){
						if(mtm.getValueAt(row, col) == null) strData = "";
						else strData = mtm.getValueAt(row, col).toString();

						hmRowData.put(mtm.getColumnName(col), strData);
					}
				}		

				setDataToMasterAdd(hmRowData); //Fill the form below from selected row
				System.out.println(" HM "+hmRowData);
			}
		});
////////////////////////////
		
		
	return panelMasters;
	}
	
	//METHOD FILL MASTER ADD FORM
	 /**
	  * Fill form of master from HashMap
	  * @param hmMaster
	  */
		void setDataToMasterAdd(HashMap<String, String> hmMaster){ // Fill the form of master from HashMap
			txtId.setText(hmMaster.get("id"));
			txtName.setText(hmMaster.get("fname"));
			txtMName.setText(hmMaster.get("mname"));
			txtLName.setText(hmMaster.get("lname"));
			txtCellPhone.setText(hmMaster.get("cell"));
			txtHomePhone.setText(hmMaster.get("phone"));
			txtAddress.setText(hmMaster.get("address"));
			txtTypeContract.setText(hmMaster.get("typeContract"));
			txtSalary.setText(hmMaster.get("masterprice"));
			txtSDateContract.setText(hmMaster.get("sdatecontract"));
			txtEDateContract.setText(hmMaster.get("edatecontract"));
			txtLocation.setText(hmMaster.get("location"));
			
		}
		
		
		/**
		 * Returns data of master from form
		 * @return HashMap<String, String>
		 */
		HashMap<String, String> getDataFromMasterAdd(){  // Take text from field and return HashMap
			HashMap hmMaster = new HashMap<String, String>();
				hmMaster.put("id", txtId.getText());
				hmMaster.put("fname", txtName.getText());
				hmMaster.put("mname", txtMName.getText());
				hmMaster.put("lname", txtLName.getText());
				hmMaster.put("cell", txtCellPhone.getText());
				hmMaster.put("phone", txtHomePhone.getText());
				hmMaster.put("address", txtAddress.getText());
				hmMaster.put("typeContract", txtTypeContract.getText());
				hmMaster.put("masterprice", txtSalary.getText());
				hmMaster.put("sdatecontract", txtSDateContract.getText());
				hmMaster.put("edatecontract", txtEDateContract.getText());
				hmMaster.put("location", txtLocation.getText());
			return hmMaster;
		}
		
		/**
		 * Check if Master form fields was already filled
		 * @param hm
		 * @return
		 */
		Boolean isAllReqFieldsFilled(HashMap<String, String> hm){
			// Check the fields if they not empty
			if (!hm.get("fname").equals("") && !hm.get("lname").equals("") && !hm.get("typeContract").equals("") &&
					!hm.get("location").equals("") && !hm.get("masterprice").equals("") && !hm.get("sdatecontract").equals("")
					&& !hm.get("edatecontract").equals(""))
			{
				return true;
			} return false;
		}
		
		
/////////////////////////////////		
//ACTION CLASS: Save date to DB//
/////////////////////////////////

  class ActLstSaveDataMasters implements ActionListener{

	  @Override
	  public void actionPerformed(ActionEvent e) {

		  HashMap<String, String> hmDataMaster = getDataFromMasterAdd();

		  if (action.equals("") ){
			  JOptionPane.showMessageDialog(null, "Выберите действие. Нечего сохранять.");
			  return;
		  }

		  System.out.println("hmDataMaster : "+hmDataMaster);
		  if(isAllReqFieldsFilled(hmDataMaster)){
			  CachedRowSet crs = mtm.getCashRowSet();
			  try {
				  crs.beforeFirst();;
				  while (crs.next()) {

					  if(crs.getInt("id") == Integer.parseInt(hmDataMaster.get("id"))){
							crs.updateString("fname", hmDataMaster.get("fname"));
							crs.updateString("mname", hmDataMaster.get("mname"));
							crs.updateString("lname", hmDataMaster.get("lname"));
							crs.updateString("cell", hmDataMaster.get("cell"));
							crs.updateString("phone", hmDataMaster.get("phone"));
							crs.updateString("address", hmDataMaster.get("address"));
							crs.updateString("masterprice", hmDataMaster.get("masterprice"));
							crs.updateString("typeContract", hmDataMaster.get("typeContract"));
							crs.updateString("location", hmDataMaster.get("location"));
							crs.updateString("sdatecontract", hmDataMaster.get("sdatecontract"));
							crs.updateString("edatecontract", hmDataMaster.get("edatecontract"));
							
							crs.updateRow();
							crs.acceptChanges();
							
							JOptionPane.showMessageDialog(null, "Изменения были успешно сохранены.");
							
							break;
						}
					}
			  } catch (Exception e1) {
				  System.out.println("SQL exception when tried to save the new data : "+e1);
				  JOptionPane.showMessageDialog(null, "Ошибка при сохранении в БД. ERR: "+e1);
			  }


			  System.out.println(hmDataMaster);
		  }else{
			  JOptionPane.showMessageDialog(null, "Не все поля заполнены! Проверте все ли поля заполнены правильно!");
		  }

		  //Clear of fields and make fields not editable
		  setTxtEditable(false, true); 
	  }

  }
  
  class ActLstUpdateMaster implements ActionListener{

	  @Override
	  public void actionPerformed(ActionEvent e) {
		  action = "UPDATE"; // Set ACTION for UPDATING
		  setTxtEditable(true, false);

	  }

  }

  class ActLstNewMaster implements ActionListener{

	  @Override
	  public void actionPerformed(ActionEvent e) {
		  setTxtEditable(true, true);

	  }

  }

	/**
	* The method enables or disable
	*  editing JTextLabel on Master form.
	* 
	* @param show 
	*/
	void setTxtEditable(boolean show, boolean isEmpty){
	
		java.awt.Component[] comps = panelMastersDat.getComponents();
		for(int i = 0; i < comps.length ; i++){
			if (show){
				if(comps[i].getClass() ==  javax.swing.JTextField.class) {
					((JTextField)comps[i]).setEditable(true);
					if(isEmpty){
						((JTextField)comps[i]).setText("");
					}
	
				}
	
				if(comps[i].getClass() ==  javax.swing.JFormattedTextField.class) {
					((JFormattedTextField)comps[i]).setEditable(true);
					if(isEmpty){
						((JFormattedTextField)comps[i]).setText("");
					}
	
				}
	 
			}  else {
				if(comps[i].getClass() ==  javax.swing.JTextField.class){
					((JTextField)comps[i]).setEditable(false);
					if(isEmpty) ((JTextField)comps[i]).setText("");
				}	
	
				if(comps[i].getClass() ==  javax.swing.JFormattedTextField.class){
					((JFormattedTextField)comps[i]).setEditable(false);
					if(isEmpty) ((JFormattedTextField)comps[i]).setText("");
				}	
			}	 			
		}
		txtId.setEditable(false);
	}
	
	
}
