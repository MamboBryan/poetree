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
        self
            .frame(width: metric.size.width, height: metric.size.height)
            .position(x: metric.size.width/2, y: metric.size.height/2)
    }
}
