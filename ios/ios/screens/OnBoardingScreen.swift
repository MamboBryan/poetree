//
//  OnBoardingScreen.swift
//  ios
//
//  Created by MamboBryan on 22/10/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import common

struct OnBoardingScreen: View {
    
    @State private var currentPage = 0
    var finish : () -> Void
    
    var body: some View {
        VStack{
            HStack {
                Text("\(PoetreeApp().name())").font(Font.body.weight(.bold)).padding()
                Spacer()
                Button(action: finish){
                    Text("Skip")
                }.modifier(Clear())
            }

            TabView(selection: $currentPage){
                OnBoardItem(position: 0).tag(0)
                OnBoardItem(position: 1).tag(1)
                OnBoardItem(position: 2).tag(2)
            }.tabViewStyle(.page)
                .indexViewStyle(.page(backgroundDisplayMode: .always))
            
            HStack{
                if(currentPage != 0){
                    Button(action: back){
                        Text("Back")
                    }.modifier(Outlined()).padding(.leading)
                }
                
                Spacer()
                
                Button(action: next){
                    Text("\(getTitle())")
                }.modifier(Filled())
                    .padding(.trailing)
            }
        }
    }
    
    func next(){
        if(currentPage == 2){
            finish()
        } else {
            currentPage += 1
        }
    }
    
    func back(){
        if(currentPage != 0){
            currentPage -= 1
        }
    }
    
    func getTitle() -> String {
        var title : String
    
         if currentPage == 2 {
             title = "Finish"
        } else {
             title = "Next"
        }
        
        return title
    }
    
}

struct OnBoardingScreen_Previews: PreviewProvider {
    static var previews: some View {
        OnBoardingScreen(finish: {})
    }
}
