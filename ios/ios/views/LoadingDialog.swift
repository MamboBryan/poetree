//
//  LoadingDialog.swift
//  ios
//
//  Created by MamboBryan on 01/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct LoadingDialog: View {
    
    @State var animate = false
    
    let color = Color("Primary")
    let color1 = Color("Primary").opacity(0.75)
    let style = StrokeStyle(lineWidth:5, lineCap: .round)
    
    var body: some View {
        GeometryReader { metric in
            ZStack{
                VStack {
                    Circle()
                         .trim(from: 0, to: 0.75)
                         .stroke(
                             AngularGradient(gradient: .init(colors: [color,color1]), center: .center), style: style
                         )
                         .rotationEffect(Angle(degrees: animate ? 360 : 0))
                         .animation(Animation.linear(duration: 0.7).repeatForever(autoreverses: false))
                         .frame(width: 36, height: 36, alignment: .center)
                         .padding(48)
                        
                }
                .background(Color.white)
                .cornerRadius(10)
               
            }
            .onAppear(){
                animate.toggle()
            }
            .frame(width: metric.size.width, height: metric.size.height, alignment: .center)
            .background(Color.black.opacity(0.25))
        }
    }
}

struct LoadingDialog_Previews: PreviewProvider {
    static var previews: some View {
            LoadingDialog()
    }
}
