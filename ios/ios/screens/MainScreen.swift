//
//  MainScreen.swift
//  ios
//
//  Created by MamboBryan on 22/09/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import common

enum MainScreenSection {
    case started, authentication, setup
}

struct MainScreen: View {
    
    @State var section = MainScreenSection.started
    
    var body: some View {
        
        ZStack {
            
            if section == MainScreenSection.started {
                VStack (alignment: .leading) {
                    Text("\(PoetreeApp().name())").font(Font.largeTitle.weight(.bold)).padding([.top], 24)
                    Text("\(PoetreeApp().dummyPoem())").padding([.top], 8).padding([.bottom], 24)
                    Text("\(PoetreeApp().dummyPoet())").font(.body.weight(.medium))
                    
                    Spacer()
                    
                    Button(action: { section = .authentication }, label: {
                        HStack{
                            Spacer()
                            Text("Get Started")
                            Spacer()
                        }
                    }).filled()
                       
                }
            }
            
            if section == MainScreenSection.authentication {
                AuthScreen()
            }
            
            
        }.padding()
        
        
    }
    
}

struct MainScreen_Previews: PreviewProvider {
    static var previews: some View {
        MainScreen()
    }
}
