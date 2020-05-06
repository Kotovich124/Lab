using System;
using System.IO;
using System.Xml.Serialization;

namespace Tincharova_2
{
    [Serializable]
    public struct XPoint
    {

        public double x;

        public XPoint(int x)
        {
            this.x=x;
        }

        public double getX()
        {
            return x;
        }
        public double process(double p, double a)
        {
            double l = p * p * Math.Sin(2 * a *Math.PI/180) / 9.8;
            x = x+l;
            return l;
        }

    }
    

    class Program
    {
        public static void PrintState(XPoint x)
        {
            Console.WriteLine("Текущее состояние точки: ");
            Console.WriteLine("Координата х: " + x.getX());
            Console.WriteLine();
        }

        static void Main(string[] args)
        {
            Console.WriteLine("Приветствие!");
            string answer;
            string path = "myxml.xml";
            XmlSerializer serializer = new XmlSerializer(typeof(XPoint));
            XPoint x = new XPoint();
            try
            {
                using (FileStream fs = new FileStream(path, FileMode.Open))
                    x = (XPoint)serializer.Deserialize(fs);
                Console.WriteLine("Состояние было восстановлено");
            }
            catch (FileNotFoundException e)
            {
                Console.WriteLine("Файл сохранений не найден. Состояние не было восстановлено");
            }
            while (true)
            {
                PrintState(x);
                Console.Write("Введите комманду (help - справка): ");
                answer = Console.ReadLine();
                switch (answer)
                {
                    case "help":
                        Console.WriteLine("Вам предоставлены команды: ");
                        Console.WriteLine("\thelp - показать справку");
                        Console.WriteLine("\texit - выйти из программы");
                        Console.WriteLine("\tdrop - сбросить состояние");
                        Console.WriteLine("\tprocess - задать импульс и угол");
                        Console.WriteLine();
                        break;
                    case "exit":
                        Console.WriteLine("Прощание!");
                        break;
                    case "process":
                        try
                        {
                            Console.Write("Введите начальный импульс [0; oo]: ");
                            double p = Double.Parse(Console.ReadLine());
                            if (p<0) goto default;
                            Console.Write("Введите угол [0; 180]: ");
                            double a = Double.Parse(Console.ReadLine());
                            if (a < 0 || a > 180) goto default;
                            double length = x.process(p, a);
                            Console.WriteLine("Тело перемещено на " + length);

                        }
                        catch (FormatException e)
                        {
                            Console.WriteLine("Импульс и угол - вещественные числа!");
                        }
                        break;
                    case "drop":
                        x = new XPoint();
                        Console.WriteLine("Состояние сброшено");
                        break;
                    default:

                        Console.WriteLine("Некорректный ввод");
                        break;

                }
                using (StreamWriter sw = new StreamWriter(path))
                {
                    serializer.Serialize(sw, x);
                }
                if (answer == "exit") break;
            }
        }
    }
}
