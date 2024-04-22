import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ArtiklComponent } from './components/main/artikl/artikl.component';
import { DobavljacComponent } from './components/main/dobavljac/dobavljac.component';
import { PorudzbinaComponent } from './components/main/porudzbina/porudzbina.component';
import { StavkaPorudzbinaComponent } from './components/main/stavka-porudzbina/stavka-porudzbina.component';
import { AboutComponent } from './components/utility/about/about.component';
import { AuthorComponent } from './components/utility/author/author.component';
import { HomeComponent } from './components/utility/home/home.component';

const routes: Routes = [
  {path:'artikl', component: ArtiklComponent},
  {path:'dobavljac', component:DobavljacComponent},
  {path:'porudzbina', component:PorudzbinaComponent},
  {path:'stavka-porudzbine', component:StavkaPorudzbinaComponent},
  {path:'home', component:HomeComponent},
  {path:'author', component:AuthorComponent},
  {path:'about', component:AboutComponent},
  {path:'', component:HomeComponent, pathMatch:'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
