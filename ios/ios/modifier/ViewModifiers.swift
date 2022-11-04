//
//  ViewModifiers.swift
//  ios
//
//  Created by MamboBryan on 05/10/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI

extension View {
    
    func frameLayout(metric: GeometryProxy)-> some View{
        self.frame(width: metric.size.width, height: metric.size.height)
            .position(x: metric.size.width/2, y: metric.size.height/2)
    }
    
}

extension Image {
    func centerCropped() -> some View {
        GeometryReader { geo in
           return self
            .resizable()
            .scaledToFill()
            .frame(width: geo.size.width, height: geo.size.height)
            .clipped()
        }
    }
}
