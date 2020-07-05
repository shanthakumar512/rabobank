import { ErrorRecords } from './error-records'


export interface ProcessedStatement {
    result: String,
    errorRecords: ErrorRecords[];
}
