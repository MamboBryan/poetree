//
//  Pager.swift
//  ios
//
//  Created by MamboBryan on 22/10/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct Pager<Content : View>: View {
    
    @Binding var currentPage: Int
    @GestureState private var translation: CGFloat = 0
    
    let pageCount : Int
    let content: Content
    
    init(pageCount: Int, currentPage: Binding<Int>, @ViewBuilder content: () -> Content){
        self._currentPage = currentPage
        self.pageCount = pageCount
        self.content = content()
    }
    
    var body: some View {
        GeometryReader{ geometry in
            
            HStack( spacing: 0){
                self.content.frame(width: geometry.size.width)
            }.frame(width: geometry.size.width, alignment: .leading)
            .offset(x: -CGFloat(self.currentPage) * geometry.size.width)
            .offset(x: self.translation)
            .animation(.interactiveSpring(), value: currentPage)
            .animation(.interactiveSpring(), value: translation)
            .gesture(
                      DragGesture().updating(self.$translation) { value, state, _ in
                          state = value.translation.width
                      }.onEnded { value in
                          let offset = value.translation.width / geometry.size.width
                          let newIndex = (CGFloat(self.currentPage) - offset).rounded()
                          self.currentPage = min(max(Int(newIndex), 0), self.pageCount - 1)
                      }
                  )
            
        }
    }
}

//struct Pager_Previews: PreviewProvider {
//    static var previews: some View {
//        Pager()
//    }
//}
