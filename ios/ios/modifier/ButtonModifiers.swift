//
//  ButtonModifiers.swift
//  ios
//
//  Created by MamboBryan on 05/10/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI

extension Button {
    
    func filled(background: Color = Color("Primary"), foreground : Color = Color("OnPrimary"), radius: CGFloat = 10, enabled: Bool = true) -> some View {
        self.padding(.vertical, 12)
         .padding(.horizontal, 16)
         .disabled(!enabled)
         .foregroundColor(enabled ? foreground : Color.black).background(RoundedRectangle(cornerRadius: radius).foregroundColor(enabled ? background : Color.gray))
        
    }
    
    func outlined(background: Color = Color("Primary"), foreground : Color = Color("OnPrimary"), radius: CGFloat = 10) -> some View {
        self.padding(.vertical, 12)
            .padding(.horizontal, 16)
            .foregroundColor(Color("Primary"))
            .background(RoundedRectangle(cornerRadius: radius).stroke(Color("Primary"), lineWidth: 1))
            .animation(.easeInOut(duration: 0.15))
    }
    
    func clear(radius: CGFloat = 10) -> some View {
        self.padding(.vertical, 12)
            .padding(.horizontal, 16)
            .foregroundColor(Color("Primary"))
    }
}
