import SwiftUI
import common

struct ContentView: View {
    
    let isAuth: Bool = false
    
    var body: some View {
        
        GeometryReader{ metric in
            
            VStack{
                
                HStack(alignment: .center){
                    Spacer()
                    Image(systemName: "icloud.slash")
                    Text("No Internet Connection")
                    Spacer()
                }
                .padding(.vertical, 5)
                .background(Color("Primary"))
                .foregroundColor(Color("OnPrimary"))
                
                ZStack{
                    switch isAuth {
                    case true :
                        NavigationView{
                            HomeScreen()
                        }
                    case false:
                        MainScreen(metric: metric)
                    }
                }
                
                Spacer()
                
            }.frameLayout(metric: metric)
        }
        
    }
    
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
