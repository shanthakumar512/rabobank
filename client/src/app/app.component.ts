import { Component } from '@angular/core';
import { FileUploadService } from './file-upload.service';
import { HttpClient, HttpEventType, HttpResponse,HttpErrorResponse  } from '@angular/common/http';
import { ProcessedStatement } from './processed-statement';
import { ErrorRecords } from './error-records';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  selectedFiles: FileList;
  fileToUpload: File = null;
  filePath: String;
  public process: ProcessedStatement;
  title = 'File-Upload-Save';
  currentFileUpload: File;
  progress: { percentage: number } = { percentage: 0 };
  selectedFile = null;
  changeImage = false;
  isVisible= false;
  displayProcessSection=false;
  isUnSupportedFile=false;
  result : {};
  errorRecords: {};
  errorMessage={};
  constructor(private uploadService: FileUploadService) { }


  processStatement() {
   
    this.uploadService.getProcessedData().subscribe(data =>
      {
      this.result = data.result,
      this.errorRecords= data.errorRecords;
      }
    );
      console.log(this.result)
      this.isVisible=!this.isVisible;
      
    

  }

  change($event) {
    this.changeImage = true;
  
  }

  changedImage(event) {
    this.selectedFile = event.target.files[0];
  }

  upload() {
    this.progress.percentage = 0;
    this.isUnSupportedFile  = false;
    this.currentFileUpload = this.selectedFiles.item(0);
    this.displayProcessSection= !this.displayProcessSection;
    this.uploadService.postFile(this.currentFileUpload).subscribe(event => {
      if (event.type === HttpEventType.UploadProgress) {
        this.progress.percentage = Math.round(100 * event.loaded / event.total);
        console.log(this.progress.percentage);
      } else if (event instanceof HttpResponse) {
        this.isUnSupportedFile  = false;
        this.displayProcessSection= !this.displayProcessSection;
        if(event.status ==200)
        console.log(event.status);
      }
      else{
        console.log("File Not Supported Error");
        this.errorMessage=event.type;
        this.isUnSupportedFile  = true; 
        this.displayProcessSection= !this.displayProcessSection;     
      }
     

      this.selectedFiles = undefined;
    }
    );
  }

  selectFile(event) {
    this.selectedFiles = event.target.files;
  }
}
