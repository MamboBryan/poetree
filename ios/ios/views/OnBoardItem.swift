//
//  OnBoardItem.swift
//  ios
//
//  Created by MamboBryan on 22/10/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import common

struct OnBoardItem: View {
    
    var position: Int
    
    var body: some View {
        GeometryReader{ geometry in
            VStack(alignment: .leading){
                
                Image(getImageName())
                    .centerCropped()
                    .frame(height: geometry.size.height/2)
                        
                Text(getTitle())
                    .font(Font.title.weight(.bold))
                    .padding(.vertical)
                
                Text(getDescription())
                    .frame(width: geometry.size.width/1.25)
                
            }.padding(.horizontal, 24)
    
        }

    }
    
    func getTitle()-> String{
       let data = PoetreeApp().onBoardingDetails()[position]
        return data.first! as String
    }
    
    func getDescription() -> String {
        let data = PoetreeApp().onBoardingDetails()[position]
        return data.second! as String
    }
    
    func getImageName() -> String{
        switch position {
            case 0 :
                return "reader"
            case 1 :
                return "poet"
            default:
               return "community"
            }
    }
    
    
}

struct OnBoardItem_Previews: PreviewProvider {
    static var previews: some View {
        OnBoardItem(position: 0)
    }
}
