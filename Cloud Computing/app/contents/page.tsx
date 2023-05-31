import { PrismaClient } from "@prisma/client";
import AddContent from "./addContent";
import DeleteContent from "./deleteContent";
import UpdateContent from "./updateContent";

const prisma = new PrismaClient();

const getContents = async () => {
    const res = await prisma.content.findMany({
        select : {
            id : true,
            author : true,
            title : true,
            subtitle : true,
            image : true,
            articleId : true,
            article : true,
        },
    });
    return res;
};

const getArticles = async () => {
    const res = await prisma.article.findMany();
    return res;
}

const Content = async () => {
    const [contents, articles] = await Promise.all([
        getContents(),
        getArticles()
    ]);

    return (
    <div>
        <div className="mb-2">
            <AddContent articles={articles}/>
        </div>
        <table className=" table w-full">
            <thead>
                <tr>
                    <th>#</th>
                    <th>Author</th>
                    <th>Title</th>
                    <th>Subtitle</th>
                    <th>Image</th>
                    <th>Category</th>
                    <th className="text-center">Action</th>
                </tr>
            </thead>
            <tbody>
            {contents.map((content, index) => (
                <tr key = { content.id }>
                    <td> { index + 1 } </td>
                    <td> { content.author } </td>
                    <td> { content.title } </td>
                    <td> { content.subtitle } </td>
                    <td> { content.image } </td>
                    <td> { content.article.category }</td>
                    <td className="flex justify-center space-x-1">
                        <UpdateContent articles={articles} content={content}/>
                        <DeleteContent content={content}/>
                    </td>
                </tr>
            ))}
            </tbody>
        </table>
    </div>
  )
}

export default Content