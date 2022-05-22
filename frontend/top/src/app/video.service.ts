import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {UploadVideoResponse} from "./upload-video/UploadVideoResponse";


// import {FileSystemFileEntry} from "ngs-file-drop";

@Injectable({
  providedIn: 'root'
})
  export class VideoService {
  constructor(private httpClient: HttpClient) { }

  uploadVideo(fileEntry:File): Observable<UploadVideoResponse>{
     const formData = new FormData()
     formData.append('file', fileEntry, fileEntry.name);

     return this.httpClient.post<UploadVideoResponse>("http://localhost:8080/api/videos", formData);
  }
}
