package com.company;


import com.company.ssl.SendersManager;
import com.company.view.Authorization;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Main {
        public static void main(String[] args){
            Authorization authorization = new Authorization();
            authorization.launchIt(args);
    }
}