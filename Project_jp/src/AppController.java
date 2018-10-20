
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ResourceBundle;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

//EUC-KR
public class AppController implements Initializable  {

	
	public static ObservableList<User> list = FXCollections.observableArrayList();
	

	@FXML private TableView<User> table_view;
	
	//Button
	@FXML private Button btn_add;
	@FXML private Button btn_delete;  
	@FXML private Button btn_search;
	@FXML private Button btn_update;
	
	//Serch 
	@FXML private TextField text_name;
	@FXML private TextField text_phone;
	@FXML private ChoiceBox<Object> choice_region; 
	@FXML private TextField text_address;
	@FXML private ChoiceBox<Object> choice_product;
	@FXML private TextField text_amount;
	
	@FXML private Label productA;
	@FXML private Label productB;
	@FXML private Label productC;
	
	int pdcA_amount;
	int pdcB_amount;
	int pdcC_amount;
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		
		//DBがないと　Error　dialogがでる　
try {
		AppDB.DB();
} catch (Exception e) {
	try {
		Stage dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.WINDOW_MODAL);
		Parent parent = FXMLLoader.load(getClass().getResource("app_isDBwork.fxml"));
		
		Scene scene = new Scene(parent);
		dialog.initOwner(App_Main.wn);  //부모 
		dialog.setTitle("Error");
		dialog.setScene(scene);
		dialog.setResizable(false);
		dialog.show();
		dialog.setAlwaysOnTop(true);
		
		
		
		
	} catch (Exception e2) {
		// TODO: handle exception
	}

}
		
	   
         
		
		
		//choice_Box list
		Choice_Box_region();
		Choice_Box_product();
		
		// DB TableView呼ぶ.
		startTableView();
		
		
		
		//btnAdd Button
		btn_add.setOnAction(event->handleAddaction(event));
		//btnUpdate Button
		btn_update.setOnAction(event->handleUpdateAction(event));
		//btnDelect Button
		btn_delete.setOnAction(event->handleDelectAction(event));
		//btnSerch Button
		btn_search.setOnAction(event->handleSerchAction(event));
		
		
		// enterkey  search 

		text_name.setOnAction(event->handleSerchAction(event));
	    text_phone.setOnAction(event->handleSerchAction(event));
	   
	   
	    
	    product_Amount();   

	

	


	}


//	product total
	private void product_Amount() {
		// TODO Auto-generated method stub
	
		
		pdcA_amount = 0;
		pdcB_amount = 0;
		pdcC_amount = 0;
		
		for(int i=0; i<list.size();i++) {
		                                         //製品A
			if(list.get(i).getProduct().equals("제품A")) {
				pdcA_amount+=(Integer)list.get(i).getAmount();
			}
			
												//製品B
			if(list.get(i).getProduct().equals("제품B")) {
				pdcB_amount+=list.get(i).getAmount();
			}
			                                    //製品C
			if(list.get(i).getProduct().equals("제품C")) {
				pdcC_amount+=list.get(i).getAmount();
			}
		}
		
			
		String productA_Lable = Integer.toString(pdcA_amount);
		productA.setText(productA_Lable);
		
		String productB_Lable = Integer.toString(pdcB_amount);
		productB.setText(productB_Lable);
		
		String productC_Lable = Integer.toString(pdcC_amount);
		productC.setText(productC_Lable);
	
	}



	//修正button　　DBに修正
	private void handleUpdateAction(ActionEvent event) {
		// TODO Auto-generated method stub
		
		
		//테이블 선택 안했을 때 수정 다이얼 로그 안뜨게 하는방법
		//tableせんたくしなかったとき　updateDialog　出ない
		if(table_view.getSelectionModel().getSelectedIndex()!=-1) {
		
			
		// TODO Auto-generated method stub
				try {
					
					
				
				
				
				Stage dialog = new Stage(StageStyle.UTILITY);
				dialog.initModality(Modality.WINDOW_MODAL);
				dialog.initOwner(btn_add.getScene().getWindow());  //부모 
				dialog.setTitle("수정");
				
				
				Parent parent = FXMLLoader.load(getClass().getResource("app_add.fxml"));
				Scene scene = new Scene(parent);
				dialog.setScene(scene);
				dialog.setResizable(false);
				dialog.show();
				
				
				
				//메인 테이블에서 선택한 튜플 가져옴  테이블 전화번호 이용해서 기존 테이블 제거함.
				//メインtableからせんたくしたtupleを　持ってくる　　tableから電話番ごうを利用して消す；
				String name =table_view.getSelectionModel().getSelectedItem().getName();
				String phone =table_view.getSelectionModel().getSelectedItem().getPhone();
				String regin =table_view.getSelectionModel().getSelectedItem().getRegin();
				String address =table_view.getSelectionModel().getSelectedItem().getAddress();
				String product =table_view.getSelectionModel().getSelectedItem().getProduct();
				Integer amount = table_view.getSelectionModel().getSelectedItem().getAmount();
				String StringAmount = amount.toString();
				
				
				//　dialogから　地域選ごう；
				int num1 =0; 
				//초이스 가져와서 다이얼로그 지역 선택
				
				//１は서울 ２は광주 ３は부산　　セッティングする
				//if 문으로  서울 광주 부산  1 2 3 순으로 해서 초이스 다이얼로그 초기값으로 불러들이기
			
				if(regin.toString().equals("서울")) {
					num1=1;
				}else if(regin.toString().equals("광주")){
					num1=2;
				}else {
					num1=3;
				}
				
				
				int num2 =0;  //초이스 가져와서 다이얼로그 제품 선택
				//１は제품A ２は제품B ３は제품C セッティングする
				//if 문으로  제품A 제품B 제품C  1 2 3 순으로 해서 초이스 다이얼로그 초기값으로 불러들이기
				
				if(product.toString().equals("제품A")) {
					num2=1;
				}else if(product.toString().equals("제품B")){
					num2=2;
				}else {
					num2=3;
				}
				
				
				
				//Table자료 다이얼로그로 가져옴 
				//tableから　dialogにもらう
				//Main테이블에서 데이터 수정 다이얼로그로 불러오기
				//Main　tableから　data　update　dialog　呼ぶ
				TextField dialogTextName = (TextField)parent.lookup("#textName");
				dialogTextName.setText(name);
				TextField dialogTextPhone = (TextField)parent.lookup("#textPhone");
				dialogTextPhone.setText(phone);
				TextField dialogTextAddress = (TextField)parent.lookup("#textAddress");
				dialogTextAddress.setText(address);
				TextField dialogTextAmount = (TextField)parent.lookup("#textAmount");
				dialogTextAmount.setText(StringAmount);
				
				//지역 목록 추가
				//地域　list　セッティング
				@SuppressWarnings("unchecked")
				ChoiceBox<Object> choiceRegin = (ChoiceBox<Object>)parent.lookup("#choiceRegin");
				choiceRegin.setItems(FXCollections.observableArrayList(
						"서울", "광주", "부산",new Separator(),"선택"
						));
				
				choiceRegin.getSelectionModel().select(num1-1);
				
				//제품 목록 추가
				//製品　list　
				@SuppressWarnings("unchecked")
				ChoiceBox<Object> choiceProduct = (ChoiceBox<Object>)parent.lookup("#choiceProduct");
				choiceProduct.setItems(FXCollections.observableArrayList(
						"제품A", "제품B", "제품C",new Separator(),"선택"
						));
				
				choiceProduct.getSelectionModel().select(num2-1);
				
				
				
	
				
				//다이얼로그에서 취소 누르면 다얼 로그창 꺼짐.
				//dialogで　
				Button btnCancel = (Button)parent.lookup("#dialog_close");
				btnCancel.setOnAction(e->dialog.close());
				
				
				
				//다이얼로그에서 확인 버튼
				//dialog　確認　button
				Button btnSave = (Button)parent.lookup("#dialog_save");
				
		
				btnSave.setOnAction(e->{
					try {
						
						
						//선택 튜플 삭제.
						//せんたく　tuple　消す
						int selected =table_view.getSelectionModel().getSelectedIndex();
						
						
						TextField textname = (TextField)parent.lookup("#textName");
						TextField textphone = (TextField)parent.lookup("#textPhone");
						@SuppressWarnings({ "rawtypes", "unused" })
						ChoiceBox choiceregin = (ChoiceBox)parent.lookup("#choiceRegin");
						TextField textaddress = (TextField)parent.lookup("#textAddress");
						@SuppressWarnings("rawtypes")
						ChoiceBox choiceproduct = (ChoiceBox)parent.lookup("#choiceProduct");
						TextField textamount = (TextField)parent.lookup("#textAmount");
						
						
						
						String name1 = textname.getText();
						String phone1 = textphone.getText();
						String regin1 = (String) choiceRegin.getSelectionModel().getSelectedItem();
						String address1 = textaddress.getText();
						String product1 = (String) choiceproduct.getSelectionModel().getSelectedItem();
						String amount1 = textamount.getText();
						
						
						User user = new User(name1, phone1, regin1, address1, product1, Integer.parseInt(amount1));
						list.set(selected, user);  
						
						
						
						
						
						//여기서 초이스 리스트를 읽어 드림 이걸 화면에 출력 DB에 저장하면됨
						//ここでchoiceのlist　read　MySQLにsave
						Class.forName("com.mysql.cj.jdbc.Driver");
						AppDB.conn = DriverManager.getConnection(
								"jdbc:mysql://121.147.26.119:3306/project?useSSL=false&serverTimezone=UTC","an", "a1641418");
					
						AppDB.stmt = AppDB.conn.createStatement();
						
						
						
						//반드시 먼저 지우고 새로 저장해야됨. 기존에 자료 기본키가 존재한 상태에서 새로운 데이터 입력할려면 기본키 중복으로 입력이 안됨
						//必ず消した後新しいdataそsaveする
						@SuppressWarnings("unused")
						int rs = AppDB.stmt.executeUpdate("delete from project where phone='"+phone+"'"); 
						rs = AppDB.stmt.executeUpdate("insert project(name, phone, regin, address, product, amount) values('"+name1+"', '"+phone1+"', "+"'"+regin1+"', '"+address1+"', '"+product1+"', '"+amount1+"')");   
						
						
						product_Amount();
						dialog.close();
						
						
					} catch (Exception e1) {
						// TODO Auto-generated catch block
					
					}
					
					 
				});
				
		
				//수정에서 Text창에서 엔터 누르면 확인버튼 
				//update　dialog　enter　key押すと　完了
				dialogTextName.setOnAction(btnSave.getOnAction());
				dialogTextPhone.setOnAction(btnSave.getOnAction());
				dialogTextAddress.setOnAction(btnSave.getOnAction());
				dialogTextAmount.setOnAction(btnSave.getOnAction());
				
				
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				
				
				
		}else {
			
			
			
			
		}
				
	
	}

	
	
	
	
	
	// 검색 버튼 클릭 DB에서 검색후 TableView에 출력.
	// Serch button DBからけんさく
	@SuppressWarnings("unused")
	private void handleSerchAction(ActionEvent event) {
		// TODO Auto-generated method stub
		
		
		AppDB.DB();
		
		//table 아이템을 초기화  빈 화면에서 검색하기 위해.
		//table　itemを　しょきか　　空いたがめんからけんさくするため
		table_view.getItems().clear();
		
		
		
		//필드에서 문자 얻어냄.
		//text　filedから　文字をもらう
		String name = text_name.getText();
		//if(name.isEmpty()) {name=null;}
		String phone = text_phone.getText();
		String regin =(String) choice_region.getSelectionModel().getSelectedItem();
		//if(regin.isEmpty()) {regin=null;}
		String address ;
		String product =(String) choice_product.getSelectionModel().getSelectedItem();
		Integer amount ;

	
		
		
		
	
		try {
			
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		AppDB.conn = DriverManager.getConnection(
				"jdbc:mysql://121.147.26.119:3306/project?useSSL=false&serverTimezone=UTC","an", "a1641418");
		
		
		AppDB.stmt = AppDB.conn.createStatement();
		//ResultSet rs = AppDB.stmt.executeQuery("select *from project where name = '"+name+"' and" );   

	
		//검색 조건에 따라 달리 하는 sql문.
		//　入力のかずによるけんさく
		
		ResultSet rs =null;
		
	        //아무것도 입력 안했을때 전체DB불러드림
		//　何も入力しないときけんさく
		   if(name.isEmpty()&&phone.isEmpty()&&regin.equals("선택")&&product.equals("선택")) {
			AppDB.DB();
		    }
		
		//순서대로 입력시 검색
		//順番で入力　   
		     if(!name.isEmpty()&&phone.isEmpty()) {
		      rs = AppDB.stmt.executeQuery("select *from project where name = '"+name+"'"); 
	         }
		 	
		    if(!name.isEmpty()&&!phone.isEmpty()) {
			      rs = AppDB.stmt.executeQuery("select *from project where name = '"+name+"' and phone ='"+phone+"'"); 
		        }
		    if(!name.isEmpty()&&!phone.isEmpty()&&!regin.equals("선택")) {
			      rs = AppDB.stmt.executeQuery("select *from project where name = '"+name+"' and phone ='"+phone+"' and regin ='"+regin+"'" ); 
		        }
		    if(!name.isEmpty()&&!phone.isEmpty()&&!regin.equals("선택")&&!product.equals("선택")) {
			      rs = AppDB.stmt.executeQuery("select *from project where name = '"+name+"' and phone ='"+phone+"' and regin ='"+regin+"' and product ='"+product+"'"); 
		        }
		    
		    
		    
		    //1개 씩 입력식 검색
		    //１つ　入力
		  if(name.isEmpty()&&!phone.isEmpty()) {
				 rs = AppDB.stmt.executeQuery("select *from project where phone = '"+phone+"' ORDER BY name ASC"); 
		    }
		    
		   if(name.isEmpty()&&!regin.equals("선택")&&phone.isEmpty()){
			  rs = AppDB.stmt.executeQuery("select *from project where regin = '"+regin+"' ORDER BY name ASC"); 
		      }
	        
		   if(name.isEmpty()&&phone.isEmpty()&&!product.equals("선택")){
				  rs = AppDB.stmt.executeQuery("select *from project where product = '"+product+"' ORDER BY name ASC");
			      }
		
		
		   
		   //폰 제품 
		   //電話　製品　入力
		   if(name.isEmpty()&&!phone.isEmpty()&&regin.equals("선택")&&!product.equals("선택")) {
			      rs = AppDB.stmt.executeQuery("select *from project where phone ='"+phone+"'and product ='"+product+"'"); 
		        }
		   //지역 제품 
		   //地域　製品　入力
		   if(name.isEmpty()&&phone.isEmpty()&&!regin.equals("선택")&&!product.equals("선택")) {
			      rs = AppDB.stmt.executeQuery("select *from project where regin ='"+regin+"'and product ='"+product+"' ORDER BY name ASC"); 
		        }
		   //이름 제품 
		   //名前　製品　入力
		   if(!name.isEmpty()&&phone.isEmpty()&&regin.equals("선택")&&!product.equals("선택")) {
			      rs = AppDB.stmt.executeQuery("select *from project where name ='"+name+"'and product ='"+product+"'"); 
		        }
		   
		   
		   //이름 지역 제품
		   //名前　地域　製品　入力
		   if(!name.isEmpty()&&phone.isEmpty()&&!regin.equals("선택")&&!product.equals("선택")) {
			      rs = AppDB.stmt.executeQuery("select *from project where name = '"+name+"' and regin ='"+regin+"' and product ='"+product+"' ORDER BY name ASC"); 
		        }
		   //폰 지역 제품
		   //電話　地域　製品
		   if(name.isEmpty()&&!phone.isEmpty()&&!regin.equals("선택")&&!product.equals("선택")) {
			      rs = AppDB.stmt.executeQuery("select *from project where phone = '"+phone+"' and regin ='"+regin+"' and product ='"+product+"'"); 
		        }
		 
		  
				
		 
		
		//DB에서 불러서 읽음
		//DBでよむ   
		
		while(rs.next()) {
			name = rs.getString("name");
			phone = rs.getString("phone");
			regin = rs.getString("regin");
			address = rs.getString("address");
			product = rs.getString("product");
			amount = rs.getInt("amount");
			
		
			User user = new User();
			user.setName(name);
			user.setPhone(phone);
			user.setRegin(regin);
			user.setAddress(address);
			user.setProduct(product);
			user.setAmount(amount);
		 
			AppController.list.add(user);
		}
		 
		
		
		 } catch (Exception e) {
				// TODO: handle exception
		}
		
		product_Amount();

	}


	// 삭제 버튼 클릭 DB및 테이블 자료 삭제.
	//削除　button　DBも削除
	private void handleDelectAction(ActionEvent event) {
		// TODO Auto-generated method stub
		
		try {
			
	
		//DB선택 목록 삭제
		//DBせんたく　list　消す	
		String phone = table_view.getSelectionModel().getSelectedItem().getPhone();
	
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		AppDB.conn = DriverManager.getConnection(
				"jdbc:mysql://121.147.26.119:3306/project?useSSL=false&serverTimezone=UTC","an", "a1641418");
		
		
		AppDB.stmt = AppDB.conn.createStatement();
		@SuppressWarnings("unused")
		int rs = AppDB.stmt.executeUpdate("delete from project where phone='"+phone+"'");   
				//"insert into(name, phone, regin, address, product, amount) values('"+name+"', '"+phone+"', "+"'"+regin+"', '"+address+"', '"+product+"', '"+amount+"'");                                    
	
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		//Table선택목록 삭제
		//table　せんたく　list　削除
		int selected =table_view.getSelectionModel().getSelectedIndex();
		table_view.getItems().remove(selected);
		
		product_Amount();
		
		
	}

	//추가 버튼 클릭 다이얼로그 구동및 DB업데이트 
	//追加　button　DBも追加
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void handleAddaction(ActionEvent event)  {
		// TODO Auto-generated method stub
		try {
			
		
		//dialog　がめん
		Stage dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initOwner(btn_add.getScene().getWindow());
		dialog.setTitle("추가");
		
		
		Parent parent = FXMLLoader.load(getClass().getResource("app_add.fxml"));
		Scene scene = new Scene(parent);
		dialog.setScene(scene);
		dialog.setResizable(false);
		dialog.show();
		
	
		
		//지역 목록 추가
		//地域　list　追加
		ChoiceBox<Object> choiceRegin = (ChoiceBox)parent.lookup("#choiceRegin");
		choiceRegin.setItems(FXCollections.observableArrayList(
				"서울", "광주", "부산",new Separator(),"선택"
				));
		choiceRegin.getSelectionModel().select(4);
		//제품 목록 추가
		//製品　list　追加
		ChoiceBox<Object> choiceProduct = (ChoiceBox)parent.lookup("#choiceProduct");
		choiceProduct.setItems(FXCollections.observableArrayList(
				"제품A", "제품B", "제품C",new Separator(),"선택"
				));
		choiceProduct.getSelectionModel().select(4);
		
		
		
		//다이얼로그에서 취소 누르면 다얼 로그창 꺼짐.
		//dialog　キャンセル　button
		Button btnCancel = (Button)parent.lookup("#dialog_close");
		btnCancel.setOnAction(e->dialog.close());
		
		
		
		
		TextField textname = (TextField)parent.lookup("#textName");
		TextField textphone = (TextField)parent.lookup("#textPhone");
		@SuppressWarnings("unused")
		ChoiceBox choiceregin = (ChoiceBox)parent.lookup("#choiceRegin");
		TextField textaddress = (TextField)parent.lookup("#textAddress");
		ChoiceBox choiceproduct = (ChoiceBox)parent.lookup("#choiceProduct");
		TextField textamount = (TextField)parent.lookup("#textAmount");
		
		
		
		
		
		
		//다이얼로그에서 확인 버튼
		//dialog　完了　button
		Button btnSave = (Button)parent.lookup("#dialog_save");
		btnSave.setOnAction(e->{
			try {
				
			
				
				String name1 = textname.getText();
				String phone1 = textphone.getText();
				String regin1 = (String) choiceRegin.getSelectionModel().getSelectedItem();
				String address1 = textaddress.getText();
				String product1 = (String) choiceproduct.getSelectionModel().getSelectedItem();
				String amount1 = textamount.getText();
				
				
				
			
			
				
				
				Class.forName("com.mysql.cj.jdbc.Driver");
				AppDB.conn = DriverManager.getConnection(
						"jdbc:mysql://121.147.26.119:3306/project?useSSL=false&serverTimezone=UTC","an", "a1641418");
			
				
				AppDB.stmt = AppDB.conn.createStatement();
				@SuppressWarnings("unused")
				int rs = AppDB.stmt.executeUpdate("insert project(name, phone, regin, address, product, amount) values('"+name1+"', '"+phone1+"', "+"'"+regin1+"', '"+address1+"', '"+product1+"', '"+amount1+"')");   
						//"insert into(name, phone, regin, address, product, amount) values('"+name+"', '"+phone+"', "+"'"+regin+"', '"+address+"', '"+product+"', '"+amount+"'");                                    
			
				
				
				User user = new User(name1, phone1, regin1, address1, product1, Integer.parseInt(amount1));
				list.add(0, user);  //저장한 스튜던트 객체 리스트에 저장
				dialog.close();
				
				
				product_Amount();
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
			
			}
		});
		
		
		// 다 입력후 엔터 누르면 저장
		//　enter　key　　save
		textname.setOnAction(btnSave.getOnAction());
		textphone.setOnAction(btnSave.getOnAction());
		textaddress.setOnAction(btnSave.getOnAction());
		textamount.setOnAction(btnSave.getOnAction());
		} catch (Exception e) {
			// TODO: handle exception
		}
		
			
		
	}


	
	//Choice_Box
	public void Choice_Box_region() {
		
		choice_region.setItems(FXCollections.observableArrayList(
				"서울","광주","부산",new Separator(),"선택"
				));
		//초이스 박스 기본값설정
		//choice　box　基本設定
		choice_region.getSelectionModel().select(4);
	}

	public void Choice_Box_product() {
		
		choice_product.setItems(FXCollections.observableArrayList(
				"제품A","제품B","제품C",new Separator(),"선택"
				));
		
		//초이스 박스 기본값설정
		//choice　box　基本設定
		choice_product.getSelectionModel().select(4);
	}
	
	
	
	
	//DB자료를 TableView에 출력
	//DB　Dataを　Tableviewに　渡す
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void startTableView() {
		for(int i =0 ; i<list.size(); i++) {
			TableColumn tc1 = table_view.getColumns().get(0);
			tc1.setCellValueFactory(new PropertyValueFactory("name"));
			
			TableColumn	 tc = table_view.getColumns().get(1);
			tc.setCellValueFactory(new PropertyValueFactory("phone"));
			
			 tc = table_view.getColumns().get(2);
			tc.setCellValueFactory(new PropertyValueFactory("regin"));
			
			 tc = table_view.getColumns().get(3);
			tc.setCellValueFactory(new PropertyValueFactory("address"));
			
			 tc = table_view.getColumns().get(4);
				tc.setCellValueFactory(new PropertyValueFactory("product"));
		
			tc = table_view.getColumns().get(5);
			 tc.setCellValueFactory(new PropertyValueFactory("amount"));
			 
			 table_view.setItems(list);
			  
			
			}
		
		
	}
	

	
	
}




