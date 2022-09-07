import { HttpHeaders } from "@angular/common/http";

export abstract class Service {

    defaultJsonHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });

}