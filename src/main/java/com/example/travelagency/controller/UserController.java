package com.example.travelagency.controller;

import com.example.travelagency.Main;
import com.example.travelagency.entity.Destination;
import com.example.travelagency.entity.Package;
import com.example.travelagency.entity.User;
import com.example.travelagency.service.DestinationService;
import com.example.travelagency.service.PackageService;
import com.example.travelagency.service.UserService;
import com.example.travelagency.repository.DestinationRepository;
import com.example.travelagency.repository.PackageRepository;
import com.example.travelagency.repository.UserRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    PackageRepository packageRepository = new PackageRepository();
    PackageService packageService = new PackageService(packageRepository);

    UserRepository userRepository = new UserRepository();
    UserService userService = new UserService(userRepository);

    DestinationRepository destinationRepository = new DestinationRepository();
    DestinationService destinationService = new DestinationService(destinationRepository);

    @FXML private ListView<String> listViewUser;

    @FXML private TableView<Package> userTableView;
    @FXML private TableColumn<Package, String> name;
    @FXML private TableColumn<Package, String> period;
    @FXML private TableColumn<Package, Integer> price;
    @FXML private TableColumn<Package, String> status;
    @FXML private TableColumn<Package, String> extraDetails;
    @FXML private TableColumn<Package, Integer> noOfPeople;

    @FXML private TextField destinationTextFieldError;
    @FXML private TextField packageTextFieldError;

    @FXML private TextField destFilter;
    @FXML private TextField nameFilter;
    @FXML private DatePicker periodFilter;
    @FXML private TextField priceFilter;
    @FXML private TextField statusFilter;
    @FXML private TextField noOfPeopleFilter;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       // System.out.println(LoginController.getCurrUser().getUserName());
        initializeHeaders();
        refreshLists();
    }
    private void initializeHeaders(){
        this.name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        this.period.setCellValueFactory(new PropertyValueFactory<>("Period"));
        this.price.setCellValueFactory(new PropertyValueFactory<>("Price"));
        this.status.setCellValueFactory(new PropertyValueFactory<>("Status"));
        this.extraDetails.setCellValueFactory(new PropertyValueFactory<>("extraDetails"));
        this.noOfPeople.setCellValueFactory(new PropertyValueFactory<>("noOfPeople"));
    }

    void refreshLists(){
        listViewUser.getItems().clear();
        userTableView.getItems().clear();
        List<Destination> destinations = destinationService.getAllDestinations();
        ObservableList<String> destinationsList = FXCollections.observableArrayList();
        for(Destination destination: destinations){
            destinationsList.add(destination.getCountry());
        }
        listViewUser.getItems().setAll(destinationsList);
    }


    @FXML
    public void logOut() throws IOException {
        Main.setRoot("loginPage");
    }

    @FXML
    public void refresh() throws IOException {
        try{
            Main.setRoot("userPage");
            refreshLists();
        }
        catch (Exception ignored){}
    }

    private void clear(){
        destinationTextFieldError.clear();
        packageTextFieldError.clear();
    }

    public Destination getSelectedItem(){
        String dest = listViewUser.getSelectionModel().getSelectedItem();
        return destinationService.getDestinationByCountry(dest);
    }


    @FXML
    public void viewPackages() throws IOException {
        clear();
        try {
            userTableView.getItems().clear();
            initializeHeaders();
            Destination currentDestination = getSelectedItem();
            userTableView.getItems().addAll(currentDestination.getPackageList());
        }catch (Exception e){
            destinationTextFieldError.setText("Select a destination first");
        }
    }

    @FXML
    public void viewAllPackages(){
        clear();
        try {
            userTableView.getItems().clear();
            List<Package> packages = packageService.getAllNotBookedPackages();
            userTableView.getItems().addAll(packages);
        }catch(Exception e){
            packageTextFieldError.setText("Could not load packages");
        }
    }

    public Package getSelectedPackage(){
        return userTableView.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void bookVacation() throws IOException {
        User user = LoginController.getCurrUser();
        Package pkg = getSelectedPackage();
        for(Package userPkg : user.getPackages()){
            if(userPkg.getId()==pkg.getId()){
                packageTextFieldError.setText("Cannot book the same package 2 times");
                return;
            }
        }
        if(pkg == null){
            packageTextFieldError.setText("Select a package first");
            return;
        }
        if(pkg.getStatus().equals("BOOKED")){
            packageTextFieldError.setText("No available spots for this vacation");
            return;
        }
        if(pkg.getStatus().equals("NOT_BOOKED")){
            pkg.setStatus("IN_PROGRESS");
        }
        pkg.setNoOfPeople(pkg.getNoOfPeople()-1);
        if(pkg.getNoOfPeople() == 0){
            pkg.setStatus("BOOKED");
        }
        packageService.modifyPackage(pkg);
        List<Package> packages = user.getPackages();
        packages.add(pkg);
        user.setPackages(packages);
        userService.modifyUser(user);
        refresh();
        packageTextFieldError.setText("Vacation booked");
    }

    @FXML
    public void seeBookedVacations(){
        clear();
        User user = LoginController.getCurrUser();
        List<Package> packages = user.getPackages();
        try{
            userTableView.getItems().clear();
            userTableView.getItems().addAll(packages);
        }catch (Exception e){
            packageTextFieldError.setText("could not display your packages");
        }
    }

    @FXML
    public void removeBooking() throws IOException {
        clear();
        User user = LoginController.getCurrUser();
        Package pkg = getSelectedPackage();
        if(pkg == null){
            packageTextFieldError.setText("Please select a package");
            return;
        }
        user.getPackages().remove(pkg);
        pkg.setNoOfPeople(pkg.getNoOfPeople()+1);
        if(pkg.getStatus().equals("BOOKED")){
            pkg.setStatus("IN_PROGRESS");
        }
        packageService.modifyPackage(pkg);
        userService.modifyUser(user);
        refresh();
    }

    @FXML
    public void filterPackages(){
        String dest = destFilter.getText();
        String name = nameFilter.getText();
        Date period = null;
        if(periodFilter.getValue() != null)
             period = Date.valueOf(periodFilter.getValue());
        int price;
        if(priceFilter.getText().equals("")) price = 99999; else price = Integer.parseInt(priceFilter.getText());
        String status = statusFilter.getText();
        int noOfPeople;
        if(noOfPeopleFilter.getText().equals("")) noOfPeople = -1; else noOfPeople = Integer.parseInt(noOfPeopleFilter.getText());

        List<Package> packages = userTableView.getItems();
        if(packages.size() == 0){
            packageTextFieldError.setText("Please have some packages selected (booked / all / country)");
            return;
        }

        List<Package> foundPackages = packageService.filterPackages(packages,dest,name,price,period,status,noOfPeople);

        userTableView.getItems().clear();
        userTableView.getItems().addAll(foundPackages);
    }
}
