using System;
using System.Collections.Generic;
using System.Configuration;
using System.IO;
using System.Linq;
using System.Text;
using Amazon.DynamoDBv2;
using Amazon.DynamoDBv2.DataModel;

namespace AwsConsoleApp1
{
    class Program
    {
        private static AmazonDynamoDBClient client = new AmazonDynamoDBClient();
        public static void Main(string[] args)
        {
            DynamoDBContext context = new DynamoDBContext(client);
            int bookId = 101;
            Book b = new Book
            {
                Id = bookId,
                Title = "AWS DynamoDB Example",
                Authors = new List<string> { "Author 1", "Author 2"},
                Publisher="P1",
            };
            context.Save(b);
            Book retrievedBook = context.Load<Book>(bookId);

            retrievedBook.Title = "AWS DynamoDB Tutorial";
            retrievedBook.Publisher = "BEC Publishers";
            context.Save(retrievedBook);

            Book updatedBook = context.Load<Book>(bookId, new DynamoDBContextConfig
            {
                ConsistentRead=true,
            });
            if(updatedBook!=null)
                context.Delete<Book>(bookId);
            Book deleteddBook = context.Load<Book>(bookId, new DynamoDBContextConfig
            {
                ConsistentRead = true,
            });
            if (deleteddBook == null)
                Console.WriteLine("Book is deleted");
            Console.ReadLine();
        }
    }
    [DynamoDBTable("ProductCatalog")]
    public class Book {
        [DynamoDBHashKey]
        public int Id { set; get; }
        [DynamoDBProperty]
        public string Title { set; get; }
        [DynamoDBProperty]
        public List<string> Authors { set; get; }
        [DynamoDBProperty]
        public string Publisher { set; get; }
    }
}