package service;

public class ClearService {

    void clearUsers(){

    }

    void clearGames(){}

    void clearAuths(){}
    void clearAll(){
        clearAuths();
        clearGames();
        clearUsers();
    }
}
