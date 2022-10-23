//
//  MainScreen.swift
//  ios
//
//  Created by MamboBryan on 22/09/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import common

enum Section {
    case started, authentication, setup
}

struct MainScreen: View {
    
    @State var section = Section.started
    
    var body: some View {
        
        ZStack {
            
            if section == Section.started {
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
                    }).modifier(Filled(background: Color("Primary"), foreground: Color("OnPrimary")))
                }
            }
            
            if section == Section.authentication {
                VStack {
                    Text("Auth")
                }
            }
            
            
        }.padding()
        
        
    }
    
}

struct MainScreen_Previews: PreviewProvider {
    static var previews: some View {
        MainScreen()
    }
}
