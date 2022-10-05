//
//  MainScreen.swift
//  ios
//
//  Created by MamboBryan on 22/09/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import common

struct MainScreen: View {
    
    var metric: GeometryProxy
    
    @State private var text = "No Internet Connection"
    let app = PoetreeApp()
    
    var body: some View {
        
        VStack(alignment: .leading){
            Text("\(app.name())").font(Font.largeTitle.weight(.bold)).padding([.top], 24)
            Text("\(app.dummyPoem())").padding([.top], 8).padding([.bottom], 24)
            Text("\(app.dummyPoet())").font(.body.weight(.medium).italic())
            
            Spacer()
            
            Button(action: {}, label: {
                HStack{
                    Spacer()
                    Text("Get Started")
                    Spacer()
                }
            }).modifier(Filled(background: Color("Primary"), foreground: Color("OnPrimary")))
        }.padding()
        
        
    }
    
}

struct MainScreen_Previews: PreviewProvider {
    static var previews: some View {
        GeometryReader{metric in
            MainScreen(metric: metric)
        }
    }
}
