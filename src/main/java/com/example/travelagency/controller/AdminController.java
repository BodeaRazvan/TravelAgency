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
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    private Destination currentDestination;
    DestinationRepository destinationRepository = new DestinationRepository();
    DestinationService destinationService = new DestinationService(destinationRepository);
    PackageRepository packageRepository = new PackageRepository();
    PackageService packageService = new PackageService(packageRepository);
    UserRepository userRepository = new UserRepository();
    UserService userService = new UserService(userRepository);
    @FXML
    private ListView<String> listViewAdmin;

    @FXML private TableView<Package> adminTableView;
    @FXML private TableColumn<Package, String> name;
    @FXML private TableColumn<Package, String> period;
    @FXML private TableColumn<Package, Integer> price;
    @FXML private TableColumn<Package, String> status;
    @FXML private TableColumn<Package, String> extraDetails;
    @FXML private TableColumn<Package, Integer> noOfPeople;

    @FXML private TextField destinationTextFieldError;
    @FXML private TextField packageTextFieldError;

    @FXML private TextField destinationTextField;

    @FXML private TextField pkgName;
    @FXML private DatePicker pkgPeriod;
    @FXML private TextField pkgPrice;
    @FXML private TextField pkgDetails;
    @FXML private TextField pkgNoOfPeople;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeHeaders();
        refreshLists();
    }

    @FXML
    public void refresh() throws IOException {
        try{
            Main.setRoot("adminPage");
            refreshLists();
        }
        catch (Exception ignored){}
    }

    void refreshLists(){
        listViewAdmin.getItems().clear();
        adminTableView.getItems().clear();
        List<Destination> destinations = destinationService.getAllDestinations();
        ObservableList<String> destinationsList = FXCollections.observableArrayList();
        for(Destination destination: destinations){
            destinationsList.add(destination.getCountry());
        }
        listViewAdmin.getItems().setAll(destinationsList);
    }

    public Destination getSelectedItem(){
        String dest = listViewAdmin.getSelectionModel().getSelectedItem();
        return destinationService.getDestinationByCountry(dest);
    }

    public Package getSelectedPackage(){
        return adminTableView.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void logOut() throws IOException {
        Main.setRoot("loginPage");
    }

    @FXML
    public void viewPackages() throws IOException {
        clear();
        try {
            adminTableView.getItems().clear();
            initializeHeaders();
            this.currentDestination = getSelectedItem();
            adminTableView.getItems().addAll(currentDestination.getPackageList());
        }catch (Exception e){
            destinationTextFieldError.setText("Select a destination first");
        }
    }

    private void initializeHeaders(){
        this.name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        this.period.setCellValueFactory(new PropertyValueFactory<>("Period"));
        this.price.setCellValueFactory(new PropertyValueFactory<>("Price"));
        this.status.setCellValueFactory(new PropertyValueFactory<>("Status"));
        this.extraDetails.setCellValueFactory(new PropertyValueFactory<>("extraDetails"));
        this.noOfPeople.setCellValueFactory(new PropertyValueFactory<>("noOfPeople"));
    }

    private void clear(){
        destinationTextFieldError.clear();
        packageTextFieldError.clear();
    }

    @FXML
    private void addDestination() throws IOException {
        clear();
        if(destinationTextField.getText().equals("")){
            destinationTextFieldError.setText("Enter a name");
            return;
        }
        Destination destination = new Destination(destinationTextField.getText(),null);
        destinationService.addDestination(destination);
        refresh();
        destinationTextFieldError.setText("Destination added");
    }

    @FXML private void addPackage(){
        clear();
        try {
            adminTableView.getItems().clear();
            initializeHeaders();
            this.currentDestination = getSelectedItem();
            try {
                if(pkgPeriod.getValue() == null){
                    packageTextFieldError.setText("Date must be added");
                    return;
                }else{
                 LocalDateTime now = LocalDateTime.now();
                 if(pkgPeriod.getValue().isBefore(ChronoLocalDate.from(now))){
                     packageTextFieldError.setText("Date must be in the future");
                     return;
                 }
                }
                if(Integer.parseInt(pkgPrice.getText())<=0){
                    packageTextFieldError.setText("Price must be > 0");
                    return;
                }
                if(Integer.parseInt(pkgNoOfPeople.getText())<=0){
                    packageTextFieldError.setText("Nr of available spots must be > 0");
                    return;
                }
                Package pkg = new Package(pkgName.getText(),Integer.parseInt(pkgPrice.getText()),
                        Date.valueOf(pkgPeriod.getValue()),pkgDetails.getText(),Integer.parseInt(pkgNoOfPeople.getText()),currentDestination);
                packageService.addPackage(pkg);
                refresh();
                packageTextFieldError.setText("Package added successfully");
            }catch (Exception e){
                e.printStackTrace();
            }
        }catch (Exception e){
            packageTextFieldError.setText("Select a destination first");
        }
    }

    @FXML private void editPackage() throws IOException {
        clear();
        Package pkg = getSelectedPackage();
        if(pkg == null){
            packageTextFieldError.setText("Select a package first");
            return;
        }
        Package newPkg = new Package(pkg.getId(),pkg.getName(),pkg.getPrice(),pkg.getPeriod(),pkg.getExtraDetails(),pkg.getNoOfPeople(),pkg.getStatus(),pkg.getUser(),pkg.getDestination());
        if(!pkgName.getText().equals("")){
            newPkg.setName(pkgName.getText());
        }
        if(!pkgPrice.getText().equals("")){
            if(Integer.parseInt(pkgPrice.getText())>0)
                newPkg.setPrice(Integer.parseInt(pkgPrice.getText()));
            else{
                packageTextFieldError.setText("Price must be >0");
                return;
            }
        }
        if(pkgPeriod.getValue() != null){
            LocalDateTime now = LocalDateTime.now();
            if(pkgPeriod.getValue().isBefore(ChronoLocalDate.from(now))){
                packageTextFieldError.setText("Date must be in the future");
                return;
            }
            newPkg.setPeriod( Date.valueOf(pkgPeriod.getValue()));
        }
        if(!pkgDetails.getText().equals("")){
            newPkg.setExtraDetails(pkgDetails.getText());
        }
        if(!pkgNoOfPeople.getText().equals("")){
            int nr = Integer.parseInt(pkgNoOfPeople.getText());
            if(nr<0){
                packageTextFieldError.setText("Cannot add negative nr of people");
                return;
            }
            if(nr==0){
                newPkg.setNoOfPeople(0);
                newPkg.setStatus("BOOKED");
            }else{
                newPkg.setNoOfPeople(nr);
                if(newPkg.getUsers().isEmpty())
                    newPkg.setStatus("NOT_BOOKED");
                else
                    newPkg.setStatus("IN_PROGRESS");
            }
        }
        try {
            packageService.modifyPackage(newPkg);
        }catch (Exception e){
            packageTextFieldError.setText("Could not edit Package");
            return;
        }
        refresh();
        packageTextFieldError.setText("Package edited successfully");
    }

    @FXML public void removePackage() throws IOException {
        clear();
        Package pkg = getSelectedPackage();
        if(pkg == null){
            packageTextFieldError.setText("Select a package first");
            return;
        }
        Package newPkg = new Package(pkg.getId(),pkg.getName(),pkg.getPrice(),pkg.getPeriod(),pkg.getExtraDetails(),pkg.getNoOfPeople(),pkg.getStatus(),pkg.getUser(),pkg.getDestination());
        try {
            packageService.removePackage(newPkg);
        }catch (Exception e){
            packageTextFieldError.setText("Could not delete package");
        }
        refresh();
        packageTextFieldError.setText("Package deleted");
    }

    @FXML public void deleteDestination() throws IOException {
        Destination destination = getSelectedItem();
        if (destination == null){
            destinationTextFieldError.setText("Select destination first");
            return;
        }
        try {
            for(Package pkg:destination.getPackageList()){
                List<User> users = pkg.getUsers();
                for(User user : users){
                    List<Package> packages = user.getPackages();
                    packages.remove(pkg);
                    user.setPackages(packages);
                    userService.modifyUser(user);
                }
            }
            destinationService.removeDestination(destination);
        }catch (Exception e){
            destinationTextFieldError.setText("Could not remove destination");
            return;
        }
        refresh();
        destinationTextFieldError.setText("Destination deleted");
    }

    @FXML
    public void viewAllPackages(){
        clear();
        try {
            adminTableView.getItems().clear();
            List<Package> packages = packageService.getAllPackages();
            adminTableView.getItems().addAll(packages);
        }catch(Exception e){
            packageTextFieldError.setText("Could not load packages");
        }
    }
}
