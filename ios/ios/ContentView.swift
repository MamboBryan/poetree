import SwiftUI
import common

struct ContentView: View {
    
    // let name = PoetreeApp().name()

	var body: some View {
		Text("Poetree")
            .background(Color("Background"))
            .foregroundColor(Color("OnBackground"))
	}

}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
