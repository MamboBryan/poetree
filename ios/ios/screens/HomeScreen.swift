//
//  HomeScreen.swift
//  ios
//
//  Created by MamboBryan on 05/10/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct HomeScreen: View {
    var body: some View {
        GeometryReader{ metric in
            VStack{
                Text("Home")
            }
        }
    }
}

struct HomeScreen_Previews: PreviewProvider {
    static var previews: some View {
        HomeScreen()
    }
}
