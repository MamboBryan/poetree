//
//  MainScreen.swift
//  ios
//
//  Created by MamboBryan on 22/09/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct MainScreen: View {
    
    var body: some View {
        VStack{
            
            HStack{
                Image(systemName: "pencil")
                Text("No Internet Connection")
            }
            .background(Color(.red))
            
            HStack {
                
            }
            
        }
    }
}

struct MainScreen_Previews: PreviewProvider {
    static var previews: some View {
        MainScreen()
    }
}
