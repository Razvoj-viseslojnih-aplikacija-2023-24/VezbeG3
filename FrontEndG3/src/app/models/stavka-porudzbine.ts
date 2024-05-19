import { Artikl } from "./artikl";
import { Porudzbina } from "./porudzbina";

export class StavkaPorudzbine{
    id!:number;
    redniBroj!:number;
    cena!:number;
    jedinicaMere!:string;
    kolicina!:number;
    artikl!:Artikl;
    porudzbina!:Porudzbina;
}