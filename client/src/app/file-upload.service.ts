import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';
import {ProcessedStatement} from './processed-statement';

@Injectable({
  providedIn: 'root'
})
export class FileUploadService {

constructor(private https: HttpClient) { }

postFile(file: File): Observable<HttpEvent<{}>> {
    const data: FormData = new FormData();
    // data.append()
    // console.log(file.size +"***** "+file.name)
    data.append('file', file);

    const newRequest = new HttpRequest('POST', 'http://localhost:8080/rabobank/savefile', data, {
      reportProgress: true,
      responseType: 'text'
    });

    return this.https.request(newRequest);
  }

  getProcessedData(): Observable<ProcessedStatement>{
    return this.https.get<ProcessedStatement>('http://localhost:8080/rabobank/processStatement');
  }


}
